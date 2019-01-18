package com.example.android_custom_view_volume.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.android_custom_view_volume.R;

public class VolumeControlView extends View {
    public static final int MAX_ANGLE = 270;
    protected Paint outerCirclePaint, innerCirclePaint, knobCirclePaint;
    protected int outerColor, innerColor, knobColor, rotationAngle, volumeIncrement;
    float centerX, centerY, outerRadius, innerRadius, knobRadius, knobDistanceFromCenter;
    double startRotationAngle;
    boolean clockwise, atBottomStop, atTopStop;
    AudioManager audioManager;


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
        outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        knobCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        int streamMaxVolume = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        volumeIncrement = MAX_ANGLE / streamMaxVolume;
        rotationAngle = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * volumeIncrement;


        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
            outerColor = typedArray.getResourceId(R.styleable.VolumeControlView_outer_color, R.color.colorPrimaryDark);
            innerColor = typedArray.getResourceId(R.styleable.VolumeControlView_inner_color, R.color.colorPrimary);
            knobColor = typedArray.getResourceId(R.styleable.VolumeControlView_knob_color, R.color.colorAccent);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
        outerRadius = (centerX < centerY ? centerX : centerY) * 0.9f;
        innerRadius = (centerX < centerY ? centerX : centerY) * 0.75f;
        knobDistanceFromCenter = (centerX < centerY ? centerX : centerY) * .55f;
        knobRadius = knobDistanceFromCenter * 0.2f;


        outerCirclePaint.setColor(getResources().getColor(outerColor));
        innerCirclePaint.setColor(getResources().getColor(innerColor));
        knobCirclePaint.setColor(getResources().getColor(knobColor));

        canvas.rotate(rotationAngle, centerX, centerY);
        canvas.drawCircle(centerX, centerY, outerRadius, outerCirclePaint);
        canvas.drawCircle(centerX, centerY, innerRadius, innerCirclePaint);
        float knobCenterX = (float) (centerX - (knobDistanceFromCenter / Math.sqrt(2)));
        float knobCenterY = (float) (centerY + (knobDistanceFromCenter / Math.sqrt(2)));
        canvas.drawCircle(knobCenterX, knobCenterY, knobRadius, knobCirclePaint);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, rotationAngle / (volumeIncrement), 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clockwise = true;
                atBottomStop = false;
                atTopStop = false;
                startRotationAngle = Math.toDegrees(Math.atan2(event.getX() - centerX, centerY - event.getY()));
                break;
            case MotionEvent.ACTION_MOVE:
                double newRotationAngle = Math.toDegrees(Math.atan2(event.getX() - centerX, centerY - event.getY()));
                double differenceAngle = newRotationAngle - startRotationAngle;
                if (differenceAngle > 0) {
                    clockwise = true;
                } else {
                    clockwise = false;
                }
                startRotationAngle = newRotationAngle;
                if (!((clockwise && atTopStop) || (!clockwise && atBottomStop))) {
                    if (Math.abs(differenceAngle) > 10) {
                        differenceAngle = clockwise ? 10 : -10;
                    }
                    rotationAngle = (int) (differenceAngle + rotationAngle);
                    if (rotationAngle > MAX_ANGLE) {
                        rotationAngle = MAX_ANGLE;
                        atBottomStop = true;
                    } else if (rotationAngle < 0) {
                        rotationAngle = 0;
                        atTopStop = true;
                    } else {
                        atBottomStop = false;
                        atTopStop = false;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
