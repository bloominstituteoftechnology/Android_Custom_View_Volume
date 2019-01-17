package com.joshuahalvorson.android_custom_view_volume;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VolumeControlView extends View {

    Paint paintOuterCircle, paintInnerCircle, paintSmallInnerCircle;
    int circleRotation;

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
        paintOuterCircle = new Paint();
        paintOuterCircle.setStyle(Paint.Style.FILL);
        paintOuterCircle.setColor(Color.parseColor("#CD5C5C")); //TODO: set parameter

        paintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintInnerCircle.setStyle(Paint.Style.STROKE);
        paintInnerCircle.setStrokeWidth(3);
        paintInnerCircle.setColor(Color.WHITE); //TODO: set parameter

        paintSmallInnerCircle = new Paint();
        paintSmallInnerCircle.setStyle(Paint.Style.FILL);
        paintSmallInnerCircle.setColor(Color.parseColor("#444B6E")); //TODO: set parameter
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2f;
        float height = getHeight() / 2f;

        circleRotation = -45;
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
        canvas.drawCircle(width - (innerCircleRadius - smallInnerCircleRadius - 20), height, smallInnerCircleRadius, paintSmallInnerCircle);

    }
}
