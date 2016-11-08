package com.example.owner.doodle;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Owner on 11/7/2016.
 */
public class Drawing {
    Path path;
    Paint paint = new Paint();
    public Drawing(int c, float w, int a, Path p) {
        paint.setColor(c);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(w);
        paint.setAlpha(a);
        path = p;
    }
}

