package com.example.android_custom_view_volume.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.android_custom_view_volume.R;

public class VolumeControlView extends View {
    private static final String TAG = "Logging";
    protected Paint outerCircle, innerCircle, knobCircle;
    protected int outerColor, innerColor, knobColor;
    protected int rotationAngle;
    float centerX, centerY, outerRadius, innerRadius, knobRadius;
    double startRotationAngle;


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

    protected void init(AttributeSet attrs) {
        outerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        knobCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        rotationAngle = 0;

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
            outerColor = typedArray.getResourceId(R.styleable.VolumeControlView_outer_color, R.color.colorPrimaryDark);
            innerColor = typedArray.getResourceId(R.styleable.VolumeControlView_inner_color, R.color.colorPrimary);
            knobColor = typedArray.getResourceId(R.styleable.VolumeControlView_knob_color, R.color.colorAccent);
            typedArray.recycle();

            outerCircle.setColor(outerColor);
            innerCircle.setColor(innerColor);
            knobCircle.setColor(knobColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
        outerRadius = (centerX < centerY ? centerX : centerY) * 0.9f;
        innerRadius = (centerX < centerY ? centerX : centerY) * 0.75f;
        knobRadius = (centerX < centerY ? centerX : centerY) * 0.1f;

        canvas.rotate(rotationAngle, centerX, centerY);
        canvas.drawCircle(centerX, centerY, outerRadius, outerCircle);
        canvas.drawCircle(centerX, centerY, innerRadius, innerCircle);
        canvas.drawCircle(centerX * .5f, centerY, knobRadius, knobCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float x = 0;
        float y = 0;
        double newRotationAngle = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // triggered when view is touched
                // get and store start point with event.getX()
                startRotationAngle = Math.toDegrees(Math.atan2(event.getX() - centerX, centerY - event.getY()));
                break;
            case MotionEvent.ACTION_MOVE:
                // triggered after ACTION_DOWN but when touch is moved
                // get end point and calculate total distance traveled
                // use the total distance traveled to calculate the desired change in rotation
                // apply that change to your rotation variable
                // you may want to use a minimum and maximum rotation value to limit the rotation
                // use the new rotation to convert to the desired volume setting
                newRotationAngle = Math.toDegrees(Math.atan2(event.getX() - centerX, centerY - event.getY()));
                double differenceAngle = newRotationAngle - startRotationAngle;
                startRotationAngle = newRotationAngle;
                Log.i(TAG, "onTouchEvent: " + differenceAngle);
                rotationAngle = (int) (differenceAngle + rotationAngle);
                if (rotationAngle > 360) {
                    rotationAngle -= 360;
                } else if (rotationAngle < 0) {
                    rotationAngle += 360;
                }
//                Log.i(TAG, "onTouchEvent: " + rotationAngle);
//                Log.i(TAG, "onTouchEvent: " + centerX + " " + centerY);
//                Log.i(TAG, "onTouchEvent: " + event.getX() + " " + event.getY());
//                Log.i(TAG, String.format("onTouchEvent: Initial X: %d, Initial Y: %d");
                invalidate(); // this will cause the onDraw method to be called again with your new values
                break;
            case MotionEvent.ACTION_UP: // triggered when touch ends
//                newRotationAngle = 0;
                break;
        }
        return true; // this indicates that the event has been processed
    }
}
