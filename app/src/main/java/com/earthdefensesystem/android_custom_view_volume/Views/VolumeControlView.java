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
    protected int knobRotation = 0, dialVolume, volumeStart = 0, volumeEnd = 0, volumeDistance = 0;
    private static final int  minRotate = 0, maxRotate = 270;
    
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
            outerCirclePaint.setColor(typedArray.getColor(R.styleable.VolumeControlView_innerCircleColor, getResources().getColor(R.color.colorOuterCircle)));
            innerCirclePaint.setColor(typedArray.getColor(R.styleable.VolumeControlView_outerCircleColor, getResources().getColor(R.color.colorInnerCircle)));
            knobPaint.setColor(typedArray.getColor(R.styleable.VolumeControlView_knobColor, getResources().getColor(R.color.colorKnob)));
            typedArray.recycle();
        }


    }
    public void setVolume(int dialVolume){
        this.dialVolume = dialVolume;
    }

    public int getVolume() {
        return dialVolume;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Action was DOWN");
                volumeStart = (int) event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Action was MOVE");
                volumeEnd = (int) event.getX();
                volumeDistance = (volumeEnd - volumeStart)/95;
                knobRotation = knobRotation + volumeDistance;
                if(knobRotation < minRotate){
                    knobRotation = minRotate;
                }
                if(knobRotation > maxRotate){
                    knobRotation = maxRotate;
                }
                setVolume((int)(knobRotation/2.55f));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Action was UP");
//                Toast.makeText(getContext(), dialVolume, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(knobRotation, width, height);

        width = getWidth() / 2f;
        height = getHeight() / 2f;
        if (width < height){
            outerCircleRadius = width * 1f;
            innerCircleRadius = width * .75f;
            knobDistanceFromCenter = width *.3f;
        } else {
            outerCircleRadius = height *1f;
            innerCircleRadius = height *.75f;
            knobDistanceFromCenter = height *.5f;
        }
        knobRadius = knobDistanceFromCenter * 0.3f;

        canvas.drawCircle(width, height, outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(width, height, innerCircleRadius, innerCirclePaint);
        canvas.drawCircle(width/2, height/2, innerCircleRadius/5, knobPaint);

        
    }

}
