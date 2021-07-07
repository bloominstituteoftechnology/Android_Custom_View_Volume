package com.rybarstudios.volumecontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class VolumeControl extends View {

    public static final int OFFSET = 15;
    public static final int ROTATION_FACTOR = 75;
    public static final float VOLUME_CONVERTER = 2.55f;
    Paint outerCircle, innerCircle, volumeKnob;
    int startPoint = 0, endPoint = 0, distanceTraveled = 0;
    int rotation = 0;
    int rotationMin = 0, rotationMax = 255;
    int currentVolume;


    public VolumeControl(Context context) {
        super(context);
        init(null);
    }

    public VolumeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VolumeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public VolumeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
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
                setCurrentVolume((int) (rotation/ VOLUME_CONVERTER));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(),currentVolume + "%", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    private void init(AttributeSet attrs) {
        outerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCircle.setStyle(Paint.Style.FILL);

        innerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCircle.setStyle(Paint.Style.STROKE);
        innerCircle.setStrokeWidth(5);

        volumeKnob = new Paint(Paint.ANTI_ALIAS_FLAG);
        volumeKnob.setStyle(Paint.Style.FILL);

        if(attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControl);
            outerCircle.setColor(typedArray.getColor(
                    R.styleable.VolumeControl_outer_circle_color, Color.BLACK));
            innerCircle.setColor(typedArray.getColor
                    (R.styleable.VolumeControl_inner_circle_color, Color.GREEN));
            volumeKnob.setColor(typedArray.getColor(
                    R.styleable.VolumeControl_volume_control, Color.GREEN));
            typedArray.recycle();
        }
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
