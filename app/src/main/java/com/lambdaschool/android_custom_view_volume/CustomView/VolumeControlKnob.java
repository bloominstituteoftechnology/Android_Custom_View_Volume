package com.lambdaschool.android_custom_view_volume.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VolumeControlKnob extends View {

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
        if (attrs != null) {

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float canvasCenterX = getWidth() / 2f;
        float canvasCenterY = getHeight() / 2f;
        float canvasMaxRadius = (canvasCenterX < canvasCenterY ? canvasCenterX : canvasCenterY) - 25;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle(canvasCenterX, canvasCenterY, canvasMaxRadius, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(canvasCenterX, canvasCenterY, canvasMaxRadius - 5, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(canvasCenterX, canvasCenterY, canvasMaxRadius - 25, paint);


        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {        // triggered each time the touch state changes

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // triggered when view is touched
                // get and store start point with event.getX()
                break;
            case MotionEvent.ACTION_MOVE: // triggered after ACTION_DOWN but when touch is moved
                // get end point and calculate total distance traveled
                // use the total distance traveled to calculate the desired change in rotation
                // apply that change to your rotation variable
                // you may want to use a minimum and maximum rotation value to limit the rotation
                // use the new rotation to convert to the desired volume setting
                invalidate(); // this will cause the onDraw method to be called again with your new values
                break;
            case MotionEvent.ACTION_UP: // triggered when touch ends
                break;
        }

        return true; // this indicates that the event has been processed

    }
}
