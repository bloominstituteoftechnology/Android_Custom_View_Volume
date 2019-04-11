package com.example.volumeknob;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class VolumeKnobView extends View {
    Paint paint,paintSmallCircle;
    float x, y, radius,smallX,smallY,smallRadius;
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

        paintSmallCircle = new Paint();
        paintSmallCircle.setColor(Color.RED);
        paintSmallCircle.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintSmallCircle.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        x = getWidth()/2;
        y = getHeight()/2;
        smallX = (float) (x-(x/2.5));
        radius = getWidth()/4;
        smallY = y;
        smallRadius = radius/16;

        canvas.drawCircle(x,y,radius,paint);
        canvas.drawCircle(smallX,smallY,smallRadius,paintSmallCircle);

        super.onDraw(canvas);
    }
}
