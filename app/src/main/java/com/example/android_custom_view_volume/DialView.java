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
public class DialView extends View {

    private Paint paint, paint1;
    private float fX;    // 図形を描画する X 座標    // (1)
    private float fY;    // 図形を描画する Y 座標    // (2)
    private float fXoriginal;    // 図形を描画する X 座標    // (1)
    private float fYoriginal;    // 図形を描画する Y 座標    // (2)
    private float fRadius;    // 円の半径    // (3)
    private double dTheta;
    private double dRotation=-60;
    int iCenterX=500;
    int iCenterY=500;
    int iDialRadius=300;
    int iDotRaidus=50;
    int iWidthCanvas ;
    int iHeightCanvas;


    public DialView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public DialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public DialView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        // 画面のサイズを取得する
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        iWidthCanvas = display.getWidth();
        iHeightCanvas = display.getHeight();

        iCenterX=iWidthCanvas/2;
        iCenterY=iHeightCanvas/2;
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
        fX = iCenterX;
        fY = iCenterY-iDialRadius;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 格子を描画する
        drawGrid(canvas, 50);
        canvas.drawCircle(iCenterX, iCenterY, 380, paint1);
        // 円を描画する
        canvas.drawCircle(fX, fY, iDotRaidus, paint);    // (6)
        int iSizeText=50;
        paint1.setTextSize(iSizeText);
        canvas.drawText("x="+Float.toString(fX),100,100,paint1);
        canvas.drawText("y="+Float.toString(fY),100,110+iSizeText,paint1);
        canvas.drawText("θ="+Double.toString(getDegree()),100,120+iSizeText*2,paint1);
        canvas.drawText("rate="+Double.toString(getPercent(dRotation)),100,130+iSizeText*3,paint1);
     }

    public double getTheta(double dCos,double dSin,double dRotation){
        dRotation=-Math.PI*dRotation/180;

        double dCosRotation=Math.cos(dRotation)
                ,dSinRotation=Math.sin(dRotation),

                dCosNew=dCosRotation*dCos-dSin*dSinRotation,
                dSinNew=dSinRotation*dCos+dCosRotation*dSin;
        double dThetac=Math.acos(dCosNew);
        double dThetas=Math.asin(dSinNew);
        if(dCosNew>=0){
            if(dSinNew>=0){
                dTheta=dThetac;
            }else{
               dTheta=2*Math.PI-dThetac;

            }

        }else{
            if(dSinNew>=0){
                dTheta=dThetac;
            }else{
                dTheta=2*Math.PI-dThetac;
            }
        }
        return dTheta;
    }



    public double getDegree(){

        return Math.toDegrees(dTheta);
     }
     public int getPercent(double dRotation){
        double dTemp=(180/(90-dRotation))*(100*dTheta/(2*Math.PI));
        if(dTemp>110){
            dTemp=0;

        }
        if(dTemp>100){
            dTemp=100;
        }

        return (int)dTemp;
     }

    public String getValues(){
        return Float.toString(fX)+","+Float.toString(fY);
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
                dTheta=getTheta(dCos,dSin,dRotation);
                if(dSin>Math.sin(Math.toRadians(dRotation))) {
                    fX = -(float) (dCos * 300) + iCenterX;
                    fY = -(float) (dSin * 300) + iCenterY;
                    int p=getPercent(dRotation);
                    if(p<25 ){
                        paint.setColor(Color.BLUE);
                    }else if(p>=25&&p<50){
                        paint.setColor(Color.GREEN);
                    }else if(p>=50&&p<75){
                        paint.setColor(Color.YELLOW);
                    }else {
                        paint.setColor(Color.RED);
                    }
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



        // 格子を描画する
        Paint paint = new Paint();        // (15)
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        for (int i = 0; i < Math.max(iWidthCanvas, iHeightCanvas); i += interval) {
            canvas.drawText(Integer.toString(i), i, paint.getTextSize(), paint);
            canvas.drawLine(i, 0, i, iHeightCanvas, paint);
            canvas.drawText(Integer.toString(i), 0, i, paint);
            canvas.drawLine(0, i, iWidthCanvas, i, paint);
        }
    }
}