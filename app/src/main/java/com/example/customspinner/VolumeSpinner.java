package com.example.customspinner;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class VolumeSpinner extends android.support.v7.widget.AppCompatImageView {

    float x = 0, y = 0;
    float touchX, touchY;
    float rotation = 0;
    private Paint paint;
    private Canvas canvas;
    private Canvas canvas2;
    private Resources resources;
    private Bitmap bitmap;

    public VolumeSpinner(Context context) {
        super(context);
        init(context);
    }

    public VolumeSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VolumeSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        canvas = new Canvas();
        canvas2 = new Canvas();
        paint = new Paint();
        resources = context.getResources();
        bitmap = BitmapFactory
                .decodeResource(resources, R.drawable.knobtrans);

        bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float diffX = event.getX() - touchX;

                rotation += diffX / 3;
                touchX = event.getX();

                invalidate();



                /*float diffX = event.getX() - touchX; //move box with your mouse
                float diffY = event.getY() - touchY;
                x += diffX;
                y += diffY;
                touchX = event.getX();
                touchY = event.getY();*/
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas2.drawColor(Color.RED);

        if (rotation > 175) rotation = 175;
        if (rotation < -100) rotation = -100;


        canvas.rotate(rotation, getPivotX() + 2, getPivotY() + 43);


        canvas.drawBitmap(bitmap, 140, 430, paint);


        //canvas.drawCircle(getPivotX() + 2, getPivotY() + 43, 15, paint); //reenable to see pivot point

    }
}
