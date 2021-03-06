package edu.umd.hcil.impressionistpainter434;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.text.MessageFormat;
import java.util.Random;

public class ImpressionistView extends View {

    private ImageView _imageView;
    private boolean bouquet;
    private boolean eraser;
    private Canvas _offScreenCanvas = null;
    private Bitmap _offScreenBitmap = null;
    private Paint _paint = new Paint();
    private Bitmap _image;
    private int _alpha = 150;
    private int _defaultRadius = 25;
    private Paint _paintBorder = new Paint();
    private BrushType _brushType = BrushType.Square;

    public ImpressionistView(Context context) {
        super(context);
        init(null, 0);
    }

    public ImpressionistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ImpressionistView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //Option setters
    public void setBouquet(boolean checked) {
        bouquet = checked;
    }
    public void setEraser(boolean checked) {
        eraser = checked;
    }


    /**
     * Because we have more than one constructor (i.e., overloaded constructors), we use
     * a separate initialization method
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle){

        // Set setDrawingCacheEnabled to true to support generating a bitmap copy of the view (for saving)
        // See: http://developer.android.com/reference/android/view/View.html#setDrawingCacheEnabled(boolean)
        //      http://developer.android.com/reference/android/view/View.html#getDrawingCache()
        this.setDrawingCacheEnabled(true);
        _paint.setColor(Color.RED);
        _paint.setAlpha(_alpha);
        _paint.setAntiAlias(true);
        _paint.setStyle(Paint.Style.FILL);
        _paint.setStrokeWidth(4);
        _paintBorder.setColor(Color.BLACK);
        _paintBorder.setStrokeWidth(3);
        _paintBorder.setStyle(Paint.Style.STROKE);
        _paintBorder.setAlpha(50);
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh){

        Bitmap bitmap = getDrawingCache();
        Log.v("onSizeChanged", MessageFormat.format("bitmap={0}, w={1}, h={2}, oldw={3}, oldh={4}", bitmap, w, h, oldw, oldh));
        if(bitmap != null) {
            _offScreenBitmap = getDrawingCache().copy(Bitmap.Config.ARGB_8888, true);
            _offScreenCanvas = new Canvas(_offScreenBitmap);
        }
    }

    /**
     * Sets the ImageView, which hosts the image that we will paint in this view
     * @param imageView
     */
    public void setImageView(ImageView imageView){
        _imageView = imageView;
        _image = _imageView.getDrawingCache();
    }

    /**
     * Sets the brush type. Feel free to make your own and completely change my BrushType enum
     * @param brushType
     */
    public void setBrushType(BrushType brushType){
        _brushType = brushType;
    }

    public Bitmap get_offScreenBitmap() {
        return _offScreenBitmap;
    }
    /**
     * Clears the painting
     */
    public void clearPainting(){
        //Draw a white rectangle over the canvas to "clear" the painting
        if(_offScreenCanvas != null){
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            _offScreenCanvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
            invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(_offScreenBitmap != null) {
            canvas.drawBitmap(_offScreenBitmap, 0, 0, _paint);
        }

        // Draw the border. Helpful to see the size of the bitmap in the ImageView
        canvas.drawRect(getBitmapPositionInsideImageView(_imageView), _paintBorder);
        _image = _imageView.getDrawingCache();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        //Get the users touch action
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        float brushRadius = _defaultRadius;

        //Ensure that the touch is within canvas bounds
        x = (x < 0) ? 0:x;
        y = (y < 0) ? 0:y;
        x = ( x >= _image.getWidth()) ? _image.getWidth() - 1:x;
        y = ( y >= _image.getHeight()) ? _image.getHeight() - 1:y;

        //Get the corresponding pixel
        int pixel = _image.getPixel(x, y);

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int history = motionEvent.getHistorySize();
                _paint.setColor(pixel);
                _paint.setStrokeWidth(brushRadius);
                //Iterated through backlogged events
                for (int i = 0; i < history; i++) {

                    float touchX = motionEvent.getHistoricalX(i);
                    float touchY = motionEvent.getHistoricalY(i);
                    Paint p = _paint;
                    //Set color to white if eraser is on
                    if (eraser){
                        p.setColor(Color.WHITE);
                    }
                    //Square brush with bouquet option that paints multiple brushstrokes
                    if (_brushType == _brushType.Square) {
                        _offScreenCanvas.drawRect(touchX, touchY, touchX + brushRadius, touchY + brushRadius, p);
                        if (bouquet) {
                            _offScreenCanvas.drawRect(touchX+200, touchY, touchX + brushRadius+200, touchY + brushRadius, p);
                            _offScreenCanvas.drawRect(touchX-200, touchY, touchX + brushRadius-200, touchY + brushRadius, p);
                            _offScreenCanvas.drawRect(touchX+100, touchY + 150, touchX + brushRadius+100, touchY + brushRadius + 150, p);
                            _offScreenCanvas.drawRect(touchX-100, touchY + 150, touchX + brushRadius-100, touchY + brushRadius + 150, p);
                            _offScreenCanvas.drawRect(touchX+100, touchY - 150, touchX + brushRadius+100, touchY + brushRadius - 150, p);
                            _offScreenCanvas.drawRect(touchX-100, touchY - 150, touchX + brushRadius-100, touchY + brushRadius - 150, p);
                        }
                    }
                    //Circle brush with bouquet option that paints multiple brushstrokes
                    if (_brushType == _brushType.Circle) {
                        _offScreenCanvas.drawCircle(touchX, touchY, brushRadius, p);
                        if (bouquet) {
                            _offScreenCanvas.drawCircle(touchX+200, touchY, brushRadius, p);
                            _offScreenCanvas.drawCircle(touchX-200, touchY, brushRadius, p);
                            _offScreenCanvas.drawCircle(touchX+100, touchY+150, brushRadius, p);
                            _offScreenCanvas.drawCircle(touchX-100, touchY+150, brushRadius, p);
                            _offScreenCanvas.drawCircle(touchX+100, touchY-150, brushRadius, p);
                            _offScreenCanvas.drawCircle(touchX-100, touchY-150, brushRadius, p);
                        }
                    }
                    //Line brush with bouquet option that paints multiple brushstrokes
                    if (_brushType == _brushType.Line) {
                        _offScreenCanvas.drawLine(touchX, touchY, touchX + brushRadius, touchY + brushRadius, p);
                        if (bouquet) {
                            _offScreenCanvas.drawLine(touchX+200, touchY, touchX + brushRadius+200, touchY + brushRadius, p);
                            _offScreenCanvas.drawLine(touchX-200, touchY, touchX + brushRadius-200, touchY + brushRadius, p);
                            _offScreenCanvas.drawLine(touchX+100, touchY+150, touchX + brushRadius+100, touchY + brushRadius+150, p);
                            _offScreenCanvas.drawLine(touchX-100, touchY+150, touchX + brushRadius-100, touchY + brushRadius+150, p);
                            _offScreenCanvas.drawLine(touchX+100, touchY-150, touchX + brushRadius+100, touchY + brushRadius-150, p);
                            _offScreenCanvas.drawLine(touchX-100, touchY-150, touchX + brushRadius-100, touchY + brushRadius-150, p);
                        }
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }


    /**
     * This method is useful to determine the bitmap position within the Image View. It's not needed for anything else
     * Modified from:
     *  - http://stackoverflow.com/a/15538856
     *  - http://stackoverflow.com/a/26930938
     * @param imageView
     * @return
     */
    private static Rect getBitmapPositionInsideImageView(ImageView imageView){
        Rect rect = new Rect();

        if (imageView == null || imageView.getDrawable() == null) {
            return rect;
        }

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int widthActual = Math.round(origW * scaleX);
        final int heightActual = Math.round(origH * scaleY);

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - heightActual)/2;
        int left = (int) (imgViewW - widthActual)/2;

        rect.set(left, top, left + widthActual, top + heightActual);

        return rect;
    }
}

