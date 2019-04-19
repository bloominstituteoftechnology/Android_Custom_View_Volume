package com.example.android_custom_view_volume;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * 指を追うように円動かす。
 * @author marunomaruno
 * @see Graphics04Activity
 */
public class GraphicsView extends View {

    private Paint paint, paint1;
    private float cx;    // 図形を描画する X 座標    // (1)
    private float cy;    // 図形を描画する Y 座標    // (2)
    private float radius;    // 円の半径    // (3)
    int iCenterX=500;
    int iCenterY=500;
    int width ;
    int height;


    public GraphicsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public GraphicsView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        iCenterX=width/2;
        iCenterY=height/2;
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLACK);    // (4)
        paint1.setStyle(Style.FILL);    // (5)


        // ペイントオブジェクトを設定する
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);    // (4)
        paint.setStyle(Style.FILL);    // (5)

        // 丸を描画する初期値を設定する
        cx = iCenterX;
        cy = iCenterY-300;
        radius = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 格子を描画する
        drawGrid(canvas, 50);
        canvas.drawCircle(iCenterX, iCenterY, 380, paint1);
        // 円を描画する
        canvas.drawCircle(cx, cy, radius, paint);    // (6)
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {    // (7)
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:    // 指をタッチした    // (8)
                assert true;    // 何もしない
                break;

            case MotionEvent.ACTION_MOVE:    // 指を動かしている    // (9)


                double dX=iCenterX-event.getX();
                double dY=iCenterY-event.getY();
                double dC=Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
                double dCos=dX/dC;
                double dSin=dY/dC;

                if(dSin>-0.8) {
                    if(cx>iCenterX){
                        if(cy>iCenterY){
                            paint.setColor(Color.RED);
                        }else{
                            paint.setColor(Color.YELLOW);
                        }

                    }else{
                        if(cy>iCenterY){
                            paint.setColor(Color.BLUE);
                        }else{
                            paint.setColor(Color.GREEN);
                        }

                    }
                    cx = -(float) (dCos * 300) + iCenterX;
                    cy = -(float) (dSin * 300) + iCenterY;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:        // 指を離した    // (12)
                assert true;    // 何もしない
                break;

            default:
                assert true;    // 何もしない
                break;
        }

        invalidate();    // (13)

        return true;    // (14)
    }
    /**
     * 画面に格子を描画する。
     * @param canvas
     * @param interval 格子を描く間隔
     */
    private void drawGrid(final Canvas canvas, int interval) {
        // 画面のサイズを取得する


        // 格子を描画する
        Paint paint = new Paint();        // (15)
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        for (int i = 0; i < Math.max(width, height); i += interval) {
            canvas.drawText(Integer.toString(i), i, paint.getTextSize(), paint);
            canvas.drawLine(i, 0, i, height, paint);
            canvas.drawText(Integer.toString(i), 0, i, paint);
            canvas.drawLine(0, i, width, i, paint);
        }
    }
}