package com.example.volumeknob;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class VolumeKnobView extends View {
    //private final int knobRadiusMin = 30, knobRadiusMax = 2, indicatorRadiusMin = 128, indicatorRadiusMax = 8;
    int indicatorColor, knobColor;
    Paint paint, indicatorCircle;
    float x, y, knobRadius, indicatorX, indicatorY, indicatorRadius, touchX, rotation;
    float knobRadiusModifier = 4, indicatorRadiusModifier = 16;

    public VolumeKnobView(Context context) {
        super(context);
        init(null);
    }

    public VolumeKnobView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VolumeKnobView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public VolumeKnobView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = event.getX() - touchX;
                rotation += diffX / 3;
                touchX = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(), String.valueOf(getRotation()), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        indicatorCircle = new Paint();

        if (attrs != null) {

            TypedArray typedArray = getContext().obtainStyledAttributes(
                    attrs, R.styleable.VolumeKnobView);
/*
            knobRadiusModifier = typedArray.getFloat(
                    R.styleable.VolumeKnobView_knob_size_percent,
                    80);

            indicatorRadiusModifier = typedArray.getFloat(
                    R.styleable.VolumeKnobView_indicator_size_percent,
                    80);
*/

            knobColor = typedArray.getColor(
                    R.styleable.VolumeKnobView_knob_color,
                    Color.BLACK);

            indicatorColor = typedArray.getColor(
                    R.styleable.VolumeKnobView_indicator_color,
                    Color.RED);
            typedArray.recycle();

        } else {
            knobColor = Color.BLACK;
            indicatorColor = Color.RED;
        }

        // changeRadii();


        y = 0;
        x = 0;
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        indicatorCircle.setFlags(Paint.ANTI_ALIAS_FLAG);
        indicatorCircle.setStyle(Paint.Style.FILL);
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    public void setKnobColor(int knobColor) {
        this.knobColor = knobColor;
    }

    public int getIndicatorColor() {
        return indicatorColor;
    }

    public int getKnobColor() {
        return knobColor;
    }

    @Override
    public float getRotation() {
        return (float) Math.abs(rotation % 360 / 3.6);
    }

/*    private void changeRadii() {
        if (indicatorRadiusModifier > 100) {
            indicatorRadiusModifier = 100;
        }
        if (indicatorRadiusModifier < 2) {
            indicatorRadiusModifier = 2;
        }
        if(knobRadiusModifier > 100){
            knobRadiusModifier = 100;
        }
        if(knobRadiusModifier < 2){
            knobRadiusModifier = 2;
        }


        float tempa = knobRadiusMin - knobRadiusMax;
        float tempb = tempa/100;
        float tempc = Math.abs(knobRadiusModifier-100);
        float tempd = tempb * tempc;
        float tempe = tempd + knobRadiusMax;
        knobRadiusModifier = tempe;

        float tempf = indicatorRadiusMin - indicatorRadiusMax;
        float tempg = tempf/100;
        float temph = Math.abs(indicatorRadiusModifier-100);
        float tempi = tempg * temph;
        float tempj = tempi + indicatorRadiusMax;
        indicatorRadiusModifier = tempj;

    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        x = getWidth() / 2;
        y = getHeight() / 2;
        paint.setColor(knobColor);
        indicatorCircle.setColor(indicatorColor);
        indicatorX = x;
        knobRadius = getWidth() / knobRadiusModifier;
        indicatorY = (float) (y + (x / 2.5));
        indicatorRadius = knobRadius / indicatorRadiusModifier;

        canvas.rotate(rotation, x, y);
        canvas.drawCircle(x, y, knobRadius, paint);
        canvas.drawCircle(indicatorX, indicatorY, indicatorRadius, indicatorCircle);

        super.onDraw(canvas);
    }
}
