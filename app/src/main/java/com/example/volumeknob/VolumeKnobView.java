package com.example.volumeknob;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class VolumeKnobView extends View {
    Paint paint;
    float x, y, radius;
    public VolumeKnobView(Context context) {
        super(context);
        init();
    }

    public VolumeKnobView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumeKnobView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VolumeKnobView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        y = 0;
        x = 0;
        radius = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        x = getWidth()/2;
        y = getHeight()/2;
        radius = getWidth()/4;
        canvas.drawCircle(x,y,radius,paint);
        super.onDraw(canvas);
    }
}
