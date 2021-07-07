package com.jakeesveld.android_custom_view_volume;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VolumeKnob extends View {
    OvalShape knob;
    OvalShape knobPointer;
    Paint paintKnob, paintKnobPointer;
    float touchX, rotation;
    int volume;

    public VolumeKnob(Context context) {
        super(context);
        init();
    }

    public VolumeKnob(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumeKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VolumeKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public int getVolume(){
        if(rotation > 120){
            rotation = 120f;
        }else if(rotation < -120){
            rotation = -120;
        }
        volume = (int) rotation + 120;
        int percentVolume = (int) (volume / 2.4);
        return percentVolume;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = event.getX() - touchX;
                rotation += diffX / 3f;
                touchX = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                volume = getVolume();
                MainActivity.setVolumeText(volume);

        }
        return true;
    }

    private void init() {
        knob = new OvalShape();
        knobPointer = new OvalShape();
        paintKnob = new Paint();
        paintKnobPointer = new Paint();
        paintKnob.setColor(Color.BLACK);
        paintKnobPointer.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(rotation > 120){
            rotation = 120f;
        }else if(rotation < -120){
            rotation = -120;
        }

        canvas.rotate(rotation, getWidth() / 2f, getHeight() / 2f);

        float height = getHeight() / 2f;
        float width = getWidth() / 2f;

        canvas.drawCircle(width, height, 500, paintKnob);
        canvas.drawCircle(width, height - 350, 25, paintKnobPointer);

        super.onDraw(canvas);
    }
}
