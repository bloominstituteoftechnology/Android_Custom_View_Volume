package com.joshuahalvorson.android_custom_view_volume;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class VolumeControlView extends View {

    Paint paintOuterCircle, paintInnerCircle, paintSmallInnerCircle;

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
        paintOuterCircle.setColor(Color.BLACK); //TODO: set parameter

        paintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintInnerCircle.setStyle(Paint.Style.STROKE);
        paintInnerCircle.setStrokeWidth(3);
        paintInnerCircle.setColor(Color.WHITE); //TODO: set parameter

        paintSmallInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSmallInnerCircle.setStyle(Paint.Style.FILL);
        paintSmallInnerCircle.setColor(Color.RED); //TODO: set parameter
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth() / 2f;
        float height = getHeight() / 2f;
        int radius = 0;

        if(width < height){
            radius = (int)(width) - 20;
        }else if (height < width){
            radius = (int)(height) - 20;
        }

        int innerCircleRadius = (int) (radius * .92f);
        int smallInnerCircleRadius = (int) (radius * .08f);

        canvas.rotate(-45, width, height);

        canvas.drawPaint(paintOuterCircle);
        canvas.drawCircle(width, height, radius, paintOuterCircle);

        canvas.drawPaint(paintInnerCircle);
        canvas.drawCircle(width, height, innerCircleRadius, paintInnerCircle);

        canvas.drawPaint(paintSmallInnerCircle);
        canvas.drawCircle(width - (innerCircleRadius - smallInnerCircleRadius - 20), height, smallInnerCircleRadius, paintSmallInnerCircle);

    }
}
