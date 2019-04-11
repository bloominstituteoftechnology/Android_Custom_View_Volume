package com.example.israel.android_custom_view_volume;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VolumeControlView extends View {

    public static int DEFAULT_KNOB_COLOR = Color.argb(255, 0,0,0);
    public static int DEFAULT_KNOB_POINTER_COLOR = Color.argb(255,0,0,255);
    private static final float VOLUME_LEVEL_MIN = 0.f;
    private static final float VOLUME_LEVEL_MAX = 100.f;

    public VolumeControlView(Context context) {
        super(context);

        init(null);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);

            knobColor = typedArray.getColor(R.styleable.VolumeControlView_knobColor, DEFAULT_KNOB_COLOR);
            knobPointerColor = typedArray.getColor(R.styleable.VolumeControlView_knobPointerColor, DEFAULT_KNOB_POINTER_COLOR);
            volumeLevel = typedArray.getFloat(R.styleable.VolumeControlView_volumeLevel, 0.f);
            // clamp volume level
            if (volumeLevel < VOLUME_LEVEL_MIN) {
                volumeLevel = VOLUME_LEVEL_MIN;
            } else if (volumeLevel > VOLUME_LEVEL_MAX) {
                volumeLevel = VOLUME_LEVEL_MAX;
            }

            typedArray.recycle();
        } else {
            knobColor = DEFAULT_KNOB_COLOR;
            knobPointerColor = DEFAULT_KNOB_POINTER_COLOR;
        }

        minRot = -120.f;
        maxRot = 120.f;
        rotSize = maxRot - minRot;
        rotPerVolumeLevel = rotSize / VOLUME_LEVEL_MAX;

        knobRot = calculateKnobRotation();
    }

    private Paint paint = new Paint();
    private int knobColor;
    private int knobPointerColor;
    private float radius;
    private boolean isDown;
    private float cx; // center of knob
    private float cy;
    private float volumeLevel;
    private float minRot;
    private float maxRot;
    private float rotSize;
    private float rotPerVolumeLevel;
    private float knobRot;
    private float levelDeltaScale = 1.f;
    private float previousX;
    private float previousY;

    public int getKnobColor() {
        return knobColor;
    }

    public int getKnobPointerColor() {
        return knobPointerColor;
    }

    public float getRadius() {
        return radius;
    }

    public float getVolumeLevel() {
        return volumeLevel;
    }

    public float getMinRot() {
        return minRot;
    }

    public float getMaxRot() {
        return maxRot;
    }

    public float getLevelDeltaScale() {
        return levelDeltaScale;
    }

    /** Clamped to 0 and 100*/
    public void setVolumeLevel(float volumeLevel) {
        this.volumeLevel = volumeLevel;
        if (this.volumeLevel < VOLUME_LEVEL_MIN) {
            this.volumeLevel = VOLUME_LEVEL_MIN;
        } else if (this.volumeLevel > VOLUME_LEVEL_MAX) {
            this.volumeLevel = VOLUME_LEVEL_MAX;
        }
        knobRot = calculateKnobRotation();
        onVolumeLevelChanged(this.volumeLevel);
    }

    public void setKnobColor(int knobColor) {
        this.knobColor = knobColor;
        invalidate();
    }

    public void setKnobPointerColor(int knobPointerColor) {
        this.knobPointerColor = knobPointerColor;
        invalidate();
    }

    /** Clamped to -180.f and maxRot*/
    public void setMinRot(float minRot) {
        this.minRot = minRot;
        if (this.minRot < -180.f) {
            this.minRot = -180.f;
        } else if (this.minRot > maxRot) {
            this.minRot = maxRot;
        }
        this.rotSize = maxRot - minRot;
        rotPerVolumeLevel = rotSize / VOLUME_LEVEL_MAX;
        knobRot = calculateKnobRotation();
    }

    /** Clamped to minRot and 180.f*/
    public void setMaxRot(float maxRot) {
        this.maxRot = maxRot;
        if (this.maxRot < minRot) {
            this.maxRot = minRot;
        } else if (this.maxRot > 180.f) {
            this.maxRot = 180.f;
        }
        this.rotSize = maxRot - minRot;
        rotPerVolumeLevel = rotSize / VOLUME_LEVEL_MAX;
        knobRot = calculateKnobRotation();
    }

    public void setLevelDeltaScale(float levelDeltaScale) {
        this.levelDeltaScale = levelDeltaScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        cx = getWidth() * 0.5f;
        cy = getHeight() * 0.5f;
        radius = cx;

        canvas.rotate(knobRot, cx, cy);
        paint.setColor(knobColor);
        canvas.drawCircle(cx, cy, radius, paint);

        paint.setColor(knobPointerColor);
        canvas.drawCircle(cx, cy + (-radius*0.8f), radius * 0.1f, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();

                if (CollisionStatics.CircleAndPoint(radius, cx, cy, x, y)) {
                    isDown = true;
                    previousX = x;
                    previousY = y;
                }

            } break;

            case MotionEvent.ACTION_MOVE: {
                // only allow if it was down within the circle
                if (!isDown) {
                    break;
                }

                // WARNING HEAVY MATH

                float x = event.getX();
                float y = event.getY();

                // stroke delta
                float strokeDeltaX = x - previousX;
                float strokeDeltaY = y - previousY;

                // if these two are zero, it will cause an infinity for normScale
                if (strokeDeltaX == 0.f && strokeDeltaY == 0.f) {
                    break;
                }

                float normScale = 1.f / (float)Math.sqrt(strokeDeltaX*strokeDeltaX + strokeDeltaY*strokeDeltaY);

                // direction of the stroke
                float strokeDirX = strokeDeltaX * normScale;
                float strokeDirY = strokeDeltaY * normScale;

                float knobRotRad = (float)Math.toRadians(knobRot);

                // turn angle into vector2d
                float sn = (float)Math.sin(knobRotRad);
                float cs = (float)Math.cos(knobRotRad);

                // direction of the knob pointer
                float knobDirX = sn;
                float knobDirY = -cs;

                // get normal. direction of the knob pointer rotation towards max
                float knobDirNormalX = -knobDirY;
                float knobDirNormalY = knobDirX;

                // the sign of dot will determine increase/decrease
                float dot = knobDirNormalX * strokeDirX + knobDirNormalY * strokeDirY;

                float levelDelta = levelDeltaScale * dot;

                volumeLevel += levelDelta;

                // clamp the volume
                if (volumeLevel < VOLUME_LEVEL_MIN) {
                    volumeLevel = VOLUME_LEVEL_MIN;
                } else if (volumeLevel > VOLUME_LEVEL_MAX) {
                    volumeLevel = VOLUME_LEVEL_MAX;
                }

                knobRot = calculateKnobRotation();

                previousX = x;
                previousY = y;

                invalidate();

                onVolumeLevelChanged(volumeLevel);
            } break;

            case MotionEvent.ACTION_UP: {

                isDown = false;

            } break;
        }

        return true;
    }

    private float calculateKnobRotation() {
        return (volumeLevel * rotPerVolumeLevel) + minRot;
    }

    protected void onVolumeLevelChanged(float volumeLevel) {

    }
}
