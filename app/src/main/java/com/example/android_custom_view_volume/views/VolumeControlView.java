package com.example.android_custom_view_volume.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.android_custom_view_volume.R;

public class VolumeControlView extends View {
    protected Paint outerCircle, innerCircle, knobCircle;
    protected int outerColor, innerColor, knobColor;


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

    public VolumeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        outerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        knobCircle = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
            outerColor = typedArray.getResourceId(R.styleable.VolumeControlView_outer_color, R.color.colorPrimaryDark);
            innerColor = typedArray.getResourceId(R.styleable.VolumeControlView_inner_color, R.color.colorPrimary);
            knobColor = typedArray.getResourceId(R.styleable.VolumeControlView_knob_color, R.color.colorAccent);
            typedArray.recycle();

            outerCircle.setColor(outerColor);
            innerCircle.setColor(innerColor);
            knobCircle.setColor(knobColor);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float outerRadius = (centerX < centerY ? centerX : centerY) * 0.9f;
        float innerRadius = (centerX < centerY ? centerX : centerY) * 0.75f;
        float knobRadius = (centerX < centerY ? centerX : centerY) * 0.1f;

//        canvas.rotate(90);
        canvas.drawCircle(centerX, centerY, outerRadius, outerCircle);
        canvas.drawCircle(centerX, centerY, innerRadius, innerCircle);
        canvas.drawCircle(centerX * .5f, centerY, knobRadius, knobCircle);


    }
}
