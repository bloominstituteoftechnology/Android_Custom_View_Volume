package com.vivekvishwanath.android_custom_view_volume.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class VolumeKnob extends View {

    Paint outerCircle, innerCircle, knobCircle;
    int outerColor, innerColor, knobColor;
    float cX, cY;

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

    private void init() {
        outerCircle = new Paint();
        outerCircle.setColor(getResources().getColor(android.R.color.holo_red_dark));
        innerCircle = new Paint();
        innerCircle.setColor(getResources().getColor(android.R.color.black));
        knobCircle = new Paint();
        knobCircle.setColor(getResources().getColor(android.R.color.holo_red_dark));


    }

    @Override
    protected void onDraw(Canvas canvas) {

        cX = getWidth() / 2f;
        cY = getHeight() / 2f;

        canvas.drawCircle(cX, cY, (cX < cY ? cX : cY) * 0.8f, outerCircle);
        canvas.drawCircle(cX, cY, (cX < cY ? cX : cY) * 0.7f, innerCircle);
        float knobCY = cY - ((cX < cY ? cX : cY) * 0.5f);
        canvas.drawCircle(cX, knobCY, knobCY * 0.1f, knobCircle);
        super.onDraw(canvas);
    }
}
