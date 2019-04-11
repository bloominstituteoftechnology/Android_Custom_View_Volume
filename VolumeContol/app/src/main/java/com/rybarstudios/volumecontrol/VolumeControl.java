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
    Paint outerCircle, innerCircle, volumeKnob;
    float startPoint = 0.0f, endPoint = 0.0f, distanceTraveled = 0.0f;
    float rotation = 0.0f;
    float rotationMin = 0.0f, rotationMax = 255.0f;


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
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPoint = event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                // get end point
                endPoint = event.getX();
                //calculate total distance traveled
                distanceTraveled = endPoint - startPoint;
                // use the total distance traveled to calculate the desired change in rotation

                // apply that change to your rotation variable
                rotation += endPoint;
                // you may want to use a minimum and maximum rotation value to limit the rotation
                if(rotation < rotationMin){
                    rotation = rotationMin;
                }
                if(rotation > rotationMax){
                    rotation = rotationMax;
                }

                // use the new rotation to convert to the desired volume setting

                // this will cause the onDraw method to be called again with your new values
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

        float radius = 100;
        if(width < height){
            radius = width - OFFSET;
        }else if (height < width){
            radius = height - OFFSET;
        }

        float innerCircleRadius = radius * .92f;
        float smallInnerCircleRadius = radius * .08f;

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
