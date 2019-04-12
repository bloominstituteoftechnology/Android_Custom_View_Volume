package com.vivekvishwanath.android_custom_view_volume.views;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.vivekvishwanath.android_custom_view_volume.R;

public class VolumeKnob extends View {

    Paint outerCircle, innerCircle, knobCircle;
    float cX, cY;
    float rotation, touchX;
    float minAngle = -150f; float maxAngle = 150f;
    AudioManager audioManager;
    int maxVolume;


    public VolumeKnob(Context context) {
        super(context);
        init(null);
    }

    public VolumeKnob(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VolumeKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public VolumeKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void setVolume(int volume) {
        if (volume >= 0 && volume <= audioManager.getStreamMaxVolume(AudioManager.USE_DEFAULT_STREAM_TYPE)) {
            rotation = minAngle + ((float) volume / audioManager.getStreamMaxVolume(AudioManager.USE_DEFAULT_STREAM_TYPE)
                    * maxAngle - minAngle);
        }
    }

    public int getVolume() {
        return audioManager.getStreamVolume(AudioManager.USE_DEFAULT_STREAM_TYPE);
    }

    private void init(AttributeSet attrs) {
        innerCircle = new Paint();
        innerCircle.setColor(getResources().getColor(android.R.color.black));
        outerCircle = new Paint();
        knobCircle = new Paint();

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeKnob);
            outerCircle.setColor(typedArray.getColor(R.styleable.VolumeKnob_border_color,
                    getResources().getColor(R.color.colorAccent)));
            knobCircle.setColor(typedArray.getColor(R.styleable.VolumeKnob_knob_color,
                    getResources().getColor(R.color.colorAccent)));
        }

        rotation = minAngle;
        audioManager = (AudioManager) getContext().getSystemService(Service.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        cX = getWidth() / 2f;
        cY = getHeight() / 2f;

        canvas.rotate(rotation, cX, cY);
        canvas.drawCircle(cX, cY, (cX < cY ? cX : cY) * 0.8f, outerCircle);
        canvas.drawCircle(cX, cY, (cX < cY ? cX : cY) * 0.7f, innerCircle);
        float knobCY = cY - ((cX < cY ? cX : cY) * 0.5f);
        canvas.drawCircle(cX, knobCY, knobCY * 0.1f, knobCircle);

        int newVolume = (int) (maxVolume * ((Math.abs(minAngle) + rotation) / (maxAngle - minAngle)));
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                    float diffX = event.getX() - touchX;
                    float changeInRotation = (maxAngle - minAngle) * (diffX / getWidth());
                    if (rotation + changeInRotation > minAngle &&
                            rotation + changeInRotation < maxAngle) {
                        rotation += (maxAngle - minAngle) * (diffX / getWidth());
                        touchX = event.getX();
                        invalidate();
                    }
                break;
            case MotionEvent.ACTION_UP:
                String volumePercent =
                        Integer.toString((100 * audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)) / maxVolume);
                Toast.makeText(getContext(),volumePercent, Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }
}
