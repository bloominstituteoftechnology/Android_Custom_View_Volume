package com.earthdefensesystem.android_custom_view_volume.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.earthdefensesystem.android_custom_view_volume.R;

import static android.content.ContentValues.TAG;

public class VolumeControlView extends View {

    float width, height, outerCircleRadius, innerCircleRadius, knobRadius, knobDistanceFromCenter;
    protected int knobRotation = 0, minRotate = 0, maxRotate = 270, dialVolume,
            volumeStart = 0, volumeEnd = 0, volumeRadius = 0;
    
    protected Paint innerCirclePaint, outerCirclePaint, knobPaint;


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
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        knobPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
            outerCirclePaint.setColor(typedArray.getColor(R.styleable.VolumeControlView_innerCircleColor, getResources().getColor(R.color.colorInnerCircle)));
            innerCirclePaint.setColor(typedArray.getColor(R.styleable.VolumeControlView_outerCircleColor, getResources().getColor(R.color.colorOuterCircle)));
            knobPaint.setColor(typedArray.getColor(R.styleable.VolumeControlView_knobColor, getResources().getColor(R.color.colorKnob)));
            typedArray.recycle();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth() / 2f;
        height = getHeight() / 2f;
        if (width < height){
            outerCircleRadius = width * 1f;
            innerCircleRadius = width * .75f;
            knobDistanceFromCenter = width *.3f;
        } else {
            outerCircleRadius = height *1f;
            innerCircleRadius = height *.75f;
            knobDistanceFromCenter = height *.3f;
        }
        knobRadius = knobDistanceFromCenter * 0.6f;

        canvas.drawCircle(width, height, outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(width, height, innerCircleRadius, innerCirclePaint);
        canvas.drawCircle(width/3, height/3, innerCircleRadius/3, knobPaint);


        canvas.rotate(knobRotation, width, height);

        innerCirclePaint.setColor(getResources().getColor(R.color.colorInnerCircle));
        outerCirclePaint.setColor(getResources().getColor(R.color.colorOuterCircle));
        knobPaint.setColor(getResources().getColor(R.color.colorKnob));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                volumeStart = (int) event.getX();
                Log.d(TAG, "Action was DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                volumeEnd = (int) event.getX();
                volumeRadius = (volumeEnd - volumeStart)/100;
                knobRotation = knobRotation + (volumeRadius);
                if(knobRotation < minRotate){
                    knobRotation = minRotate;
                }
                if(knobRotation > maxRotate){
                    knobRotation = maxRotate;
                }
                setVolume(knobRotation);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Action was UP");
                break;
        }
        return true;
    }
    public void setVolume(int dialVolume){
        this.dialVolume = dialVolume;
    }
}
