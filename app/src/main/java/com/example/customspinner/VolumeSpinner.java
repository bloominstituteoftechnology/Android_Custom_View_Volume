package com.example.customspinner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static java.lang.Math.abs;

public class VolumeSpinner extends android.support.v7.widget.AppCompatImageView {

    public static final int VOL_UPPER_BOUND = 175;
    public static final int VOL_LOWER_BOUND = -100;
    public static final int GET_AND_SET_VOLUME_MAX_VALUE = 10;
    public static final int LEFT_JUSTIFY = 140;
    public static final int TOP_JUSTIFY = 430;
    public static final int X_PIVOT_OFFSET = 2;
    public static final int Y_PIVOT_OFFSET = 43;
    public static final int PIXEL_TO_DEGREE_MODIFIER = 3;
    float touchX, touchY;
    float rotation;
    private Paint paint;
    private Bitmap bitmap;

    @Override
    public float getPivotX() { //overrides pivot points to finely tune them
        return super.getPivotX() + X_PIVOT_OFFSET;
    }

    @Override
    public float getPivotY() {
        return super.getPivotY() + Y_PIVOT_OFFSET;
    }

    public VolumeSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public VolumeSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public VolumeSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) { // initializes the drawable and the defaults
        paint = new Paint();
        Resources resources = context.getResources();
        bitmap = BitmapFactory
                .decodeResource(resources, R.drawable.knobtrans);
        bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, false);


        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeSpinner);
            setVolume(typedArray.getFloat(R.styleable.VolumeSpinner_start_volume, 0));
            typedArray.recycle();
        } else {
            setVolume(5);
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //Handles moving the volume knob
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float diffX = event.getX() - touchX;
                rotation += diffX / PIXEL_TO_DEGREE_MODIFIER;
                touchX = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) { //does the calculations for drawing
        super.onDraw(canvas);
        if (rotation < VOL_LOWER_BOUND) rotation = VOL_LOWER_BOUND; //keep rotation in bounds
        if (rotation > VOL_UPPER_BOUND) rotation = VOL_UPPER_BOUND;
        canvas.rotate(rotation, getPivotX(), getPivotY()); //finely tuned pivot points




        canvas.drawBitmap(bitmap, LEFT_JUSTIFY, TOP_JUSTIFY, paint);
        //canvas.drawCircle(getPivotX() + X_PIVOT_OFFSET, getPivotY() + Y_PIVOT_OFFSET, 15, paint); //reenable to see pivot point


        //canvas.drawCircle(getPivotX() -200, getPivotY(), 30, paint); //reenable to explore painting the dial indicator
    }

    public float getVolume() { //returns a float 0-10
        float range = abs(VOL_LOWER_BOUND) + abs(VOL_UPPER_BOUND);
        float volume = abs(VOL_LOWER_BOUND)+rotation;
        volume = (volume/range)* GET_AND_SET_VOLUME_MAX_VALUE;
        return volume;
    }
    public void setVolume(float volume) { //interprets anything above GET_AND_SET_VOLUME_MAX_VALUE as the max value
        float range = abs(VOL_LOWER_BOUND) + abs(VOL_UPPER_BOUND);
        volume = ((volume/ GET_AND_SET_VOLUME_MAX_VALUE) * range) - abs(VOL_LOWER_BOUND);
        rotation = volume;
    }
}
