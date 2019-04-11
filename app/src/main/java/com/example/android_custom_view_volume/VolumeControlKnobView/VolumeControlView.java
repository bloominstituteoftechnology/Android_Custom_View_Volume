package com.example.android_custom_view_volume.VolumeControlKnobView;

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

import com.example.android_custom_view_volume.R;

public class VolumeControlView extends View {

    private static final float CONVERT_DEGREE_TO_INT_FACTOR = 2.55f;
    private static final int MINIMUM_ROTATION_VALUE = 0;
    private static final int MAXIMUM_ROTATION_VALUE = 255;
    private static final int ROTATION_FACTOR = 95;
    private static final int EDGE_OFFSET = 40;

    protected Paint paint1, paint2, paint3;
    protected int circleRotation = 0;
    protected int distanceTraveled = 0, circleEndPoint = 0, circleStartPoint = 0;
    protected int currentVolume;

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


    protected void init(AttributeSet attrs){
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.FILL);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(4);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setStyle(Paint.Style.FILL);

        if(attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
            paint1.setColor(typedArray.getColor(R.styleable.VolumeControlView_outerCircleColor, Color.BLACK));
            paint2.setColor(typedArray.getColor(R.styleable.VolumeControlView_innerCircleColor, Color.WHITE));
            paint3.setColor(typedArray.getColor(R.styleable.VolumeControlView_knobColor, Color.RED));
            typedArray.recycle();
        }
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume =  currentVolume;
        //Log.i("currentVolume", Integer.toString(this.currentVolume));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                circleStartPoint = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                circleEndPoint = (int) event.getX();
                distanceTraveled = (circleEndPoint - circleStartPoint) / ROTATION_FACTOR;
                circleRotation = circleRotation + (distanceTraveled);
                if(circleRotation < MINIMUM_ROTATION_VALUE){
                    circleRotation = MINIMUM_ROTATION_VALUE;
                }
                if(circleRotation > MAXIMUM_ROTATION_VALUE){
                    circleRotation = MAXIMUM_ROTATION_VALUE;
                }
                setCurrentVolume((int) (circleRotation / CONVERT_DEGREE_TO_INT_FACTOR));
                Toast.makeText(getContext(), "Volume is at " + currentVolume + "%", Toast.LENGTH_SHORT).show();

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2f;
        float height = getHeight() / 2f;

        canvas.rotate(circleRotation, width, height);

        int radius = 100;
        if(width < height){
            radius = (int)(width) - EDGE_OFFSET;
        }else if (height < width){
            radius = (int)(height) - EDGE_OFFSET;
        }

        int innerCircleRadius = (int) (radius * .92f);
        int smallInnerCircleRadius = (int) (radius * .08f);

        canvas.drawCircle(width, height, radius, paint1);
        canvas.drawCircle(width, height, innerCircleRadius, paint2);
        canvas.drawCircle(
                width - (innerCircleRadius - (smallInnerCircleRadius * 2) - 80),
                height + (innerCircleRadius * .5f ),
                smallInnerCircleRadius,
                paint3);

    }
}