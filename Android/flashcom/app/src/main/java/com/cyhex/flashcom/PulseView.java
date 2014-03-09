package com.cyhex.flashcom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


public class PulseView extends View {
    private ArrayList<ShapeDrawable> shapes;

    public PulseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawPulse();
    }

    protected void onDraw(Canvas canvas) {
        for(ShapeDrawable shape : shapes ){
            shape.draw(canvas);
        }

    }

    private void drawPulse(){
        int x = 10;
        int y = 10;
        int width = 50;
        int height = 300;
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(0xff74AC23);
        shape.setBounds(x, y, x + width, y + height);
        shapes.add(shape);

    }




}