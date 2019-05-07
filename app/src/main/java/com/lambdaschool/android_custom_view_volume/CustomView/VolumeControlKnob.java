package com.lambdaschool.android_custom_view_volume.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lambdaschool.android_custom_view_volume.R;

import java.util.Locale;

public class VolumeControlKnob extends View {
    public static final float VOLUME_MIN = -100;
    public static final float VOLUME_MAX = 175;
    Paint paintBottom;
    Paint paintMiddle;
    Paint paintTop;
    Paint paintKnob;
    boolean knobMoving;
    float rotation;
    float eventX;
    float eventY;
    float volume;

    public VolumeControlKnob(Context context) {
        super(context);
        init(null);
    }

    public VolumeControlKnob(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VolumeControlKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public VolumeControlKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        paintBottom = new Paint();
        paintBottom.setColor(Color.BLACK);

        paintMiddle = new Paint();
        paintMiddle.setColor(Color.RED);

        paintTop = new Paint();
        paintTop.setColor(Color.YELLOW);

        paintKnob = new Paint();
        paintKnob.setColor(Color.GRAY);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlKnob);
            paintKnob.setStrokeWidth(typedArray.getFloat(R.styleable.VolumeControlKnob_line_thickness, 2));
            paintKnob.setColor(typedArray.getColor(R.styleable.VolumeControlKnob_line_color, Color.DKGRAY));
            paintKnob.setAntiAlias(typedArray.getBoolean(R.styleable.VolumeControlKnob_anti_aliasing, true));

            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float canvasCenterX = getWidth() / 2f;
        float canvasCenterY = getHeight() / 2f;
        float canvasMaxRadius = (canvasCenterX < canvasCenterY ? canvasCenterX : canvasCenterY) * .98f;

        canvas.rotate(rotation, canvasCenterX, canvasCenterY);

        canvas.drawCircle(canvasCenterX, canvasCenterY, canvasMaxRadius, paintBottom);
        canvas.drawCircle(canvasCenterX, canvasCenterY, canvasMaxRadius * .98f, paintMiddle);
        canvas.drawCircle(canvasCenterX, canvasCenterY, canvasMaxRadius * .91f, paintTop);
        canvas.drawCircle(canvasCenterX * .62f, canvasCenterY * .62f, canvasMaxRadius * .11f, paintKnob);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {        // triggered each time the touch state changes

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // triggered when view is touched
                // get and store start point with event.getX()
                eventX = event.getX();
                //eventY = event.getY();
                knobMoving = true;

                break;

            case MotionEvent.ACTION_MOVE: // triggered after ACTION_DOWN but when touch is moved
                // get end point and calculate total distance traveled
                // use the total distance traveled to calculate the desired change in rotation
                // apply that change to your rotation variable
                // you may want to use a minimum and maximum rotation value to limit the rotation
                // use the new rotation to convert to the desired volume setting
                float distanceX = eventX - event.getX();
                //float distanceY = eventY - event.getY();

                rotation = distanceX;// + distanceY / 2;
                if (rotation < VOLUME_MIN)
                    rotation = VOLUME_MIN;
                if (rotation > VOLUME_MAX)
                    rotation = VOLUME_MAX;

                setVolume(rotation);

                invalidate(); // this will cause the onDraw method to be called again with your new values
                break;

            case MotionEvent.ACTION_UP: // triggered when touch ends

                //Toast.makeText(getContext(), String.valueOf(rotation), (Toast.LENGTH_LONG)).show();
                Toast.makeText(getContext(), String.format(Locale.US,"%.0f",getVolume())+"%", (Toast.LENGTH_LONG)).show();

                break;
        }

        return true; // this indicates that the event has been processed
    }

    protected float getVolume() {
        return volume;
    }

    protected void setVolume(float currentVolume) {
        float volumeRange = Math.abs(VOLUME_MIN) + Math.abs(VOLUME_MAX);
        volume = (Math.abs(currentVolume) + VOLUME_MIN) / volumeRange * 100f;
    }
}
