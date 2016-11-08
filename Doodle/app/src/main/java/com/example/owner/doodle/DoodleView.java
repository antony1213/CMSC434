package com.example.owner.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Owner on 11/2/2016.
 */
public class DoodleView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private boolean rainbow;
    private int origC;

    private ArrayList<Drawing> drawings = new ArrayList<>();

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setAlpha(255);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (Drawing d : drawings) {
            canvas.drawPath(d.path, d.paint);
        }
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                origC = paint.getColor();
                break;
            case MotionEvent.ACTION_MOVE:
                Random rnd = new Random();
                path.lineTo(touchX, touchY);
                if (rainbow) {
                    paint.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                }
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(touchX, touchY);
                drawings.add(new Drawing(paint.getColor(), paint.getStrokeWidth(), paint.getAlpha(), path));
                path = new Path();
                paint.setColor(origC);
                break;
        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        drawings.clear();
        invalidate();
    }
    public void setSize(float size) {
        paint.setStrokeWidth(size);
    }
    public void setRainbow(boolean checked) {
        rainbow = checked;
    }
    public void setPaintRed(int red) {
        paint.setColor(Color.argb(paint.getAlpha(), red, Color.green(paint.getColor()), Color.blue(paint.getColor())));
    }
    public void setPaintGreen(int green) {
        paint.setColor(Color.argb(paint.getAlpha(), Color.red(paint.getColor()), green, Color.blue(paint.getColor())));
    }
    public void setPaintBlue(int blue) {
        paint.setColor(Color.argb(paint.getAlpha(), Color.red(paint.getColor()), Color.green(paint.getColor()), blue));
    }
    public void setAlpha(int a) {
        paint.setAlpha(a);
    }
}
