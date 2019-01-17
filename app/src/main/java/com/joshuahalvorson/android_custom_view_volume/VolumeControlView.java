package com.joshuahalvorson.android_custom_view_volume;

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

import java.lang.reflect.Type;

public class VolumeControlView extends View {

    Paint paintOuterCircle, paintInnerCircle, paintSmallInnerCircle;
    int circleRotation = 0;
    int distanceTraveled = 0, circleEndPoint = 0, circleStartPoint = 0;
    int currentVolume;

    public VolumeControlView(Context context) {
        super(context);
        init(null);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init(AttributeSet attrs){
        paintOuterCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintOuterCircle.setStyle(Paint.Style.FILL);

        paintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintInnerCircle.setStyle(Paint.Style.STROKE);
        paintInnerCircle.setStrokeWidth(3);

        paintSmallInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSmallInnerCircle.setStyle(Paint.Style.FILL);

        if(attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
            paintOuterCircle.setColor(typedArray.getColor(R.styleable.VolumeControlView_outerCircleColor, Color.BLACK));
            paintInnerCircle.setColor(typedArray.getColor(R.styleable.VolumeControlView_innerCircleColor, Color.WHITE));
            paintSmallInnerCircle.setColor(typedArray.getColor(R.styleable.VolumeControlView_knobColor, Color.RED));
            typedArray.recycle();
        }
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume =  currentVolume;
        //Log.i("currentVolume", Integer.toString(this.currentVolume));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // triggered each time the touch state changes
        int minimumRotation = 0;
        int maximumRotation = 255;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                circleStartPoint = (int) event.getX();
                //Log.i("circlePressed","Touched: Start point - " + Integer.toString(circleStartPoint));
                break;
            case MotionEvent.ACTION_MOVE:
                circleEndPoint = (int) event.getX();
                //Log.i("circlePressed", "End point - " + Integer.toString(circleEndPoint));
                distanceTraveled = (circleEndPoint - circleStartPoint) / 95;
                circleRotation = circleRotation + (distanceTraveled);
                if(circleRotation < minimumRotation){
                    circleRotation = minimumRotation;
                }
                if(circleRotation > maximumRotation){
                    circleRotation = maximumRotation;
                }
                setCurrentVolume((int) (circleRotation / 2.55f));
                invalidate();
                //Log.i("circlePressed", Integer.toString(distanceTraveled));
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(), "Volume is at " + currentVolume + "%", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2f;
        float height = getHeight() / 2f;

        canvas.rotate(circleRotation, width, height);

        int radius;
        radius = 100;
        if(width < height){
            radius = (int)(width) - 20;
        }else if (height < width){
            radius = (int)(height) - 20;
        }

        int innerCircleRadius = (int) (radius * .92f);
        int smallInnerCircleRadius = (int) (radius * .08f);

        canvas.drawCircle(width, height, radius, paintOuterCircle);
        canvas.drawCircle(width, height, innerCircleRadius, paintInnerCircle);
        canvas.drawCircle(width - (innerCircleRadius - (smallInnerCircleRadius * 2) - 80), height + (innerCircleRadius * .5f ), smallInnerCircleRadius, paintSmallInnerCircle);

    }
}
