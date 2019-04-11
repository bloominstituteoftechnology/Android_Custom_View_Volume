package com.rybarstudios.volumecontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class VolumeControl extends View {

    public static final int OFFSET = 15;
    public static final int ROTATION_FACTOR = 75;
    Paint outerCircle, innerCircle, volumeKnob;
    int startPoint = 0, endPoint = 0, distanceTraveled = 0;
    int rotation = 0;
    int rotationMin = 0, rotationMax = 255;


    public VolumeControl(Context context) {
        super(context);
        init();
    }

    public VolumeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VolumeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPoint = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                endPoint = (int) event.getX();
                distanceTraveled = (endPoint - startPoint) / ROTATION_FACTOR;
                rotation += distanceTraveled;
                if(rotation < rotationMin){
                    rotation = rotationMin;
                }
                if(rotation > rotationMax){
                    rotation = rotationMax;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void init() {
        outerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCircle.setColor(Color.BLACK);

        innerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCircle.setStyle(Paint.Style.STROKE);
        innerCircle.setColor(Color.GREEN);
        innerCircle.setStrokeWidth(5);

        volumeKnob = new Paint(Paint.ANTI_ALIAS_FLAG);
        volumeKnob.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float width = getWidth() / 2f;
        float height = getHeight() / 2f;

        canvas.rotate(rotation, width, height);

        int radius = 100;
        if(width < height){
            radius = (int)(width) - OFFSET;
        }else if (height < width){
            radius = (int)(height) - OFFSET;
        }

        int innerCircleRadius = (int) (radius * .92f);
        int smallInnerCircleRadius = (int) (radius * .08f);

        canvas.drawCircle(width, height, radius, outerCircle);
        canvas.drawCircle(width, height, innerCircleRadius, innerCircle);
        canvas.drawCircle(
                width - (innerCircleRadius - (smallInnerCircleRadius * 2) - 60),
                height + (innerCircleRadius * .4f ),
                smallInnerCircleRadius,
                volumeKnob);

        super.onDraw(canvas);
    }
}
