package com.example.android_custom_view_volume.activity.activity.view


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class CustomView(context: Context, attrs: AttributeSet?):
    View(context, attrs){


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG)

    private var startX: Float = 0f
    private var startY: Float = 0f
    private var diffX: Float = 0f
    private var diffY: Float = 0f
    private var endPoint: Float = 0f
    private var rotate: Float = 0f
    private var position = 0f
    private var maxRotation = 360f
    private var minRotation = 0f
    private var volume = 0f

    init {

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){

            MotionEvent.ACTION_DOWN->{
                startX = event.x



            }

            MotionEvent.ACTION_MOVE ->{
                endPoint = event.x
                diffX = endPoint-startX
                rotate = position + (diffX + 360) / width



                if (rotate >= maxRotation){
                    rotate = maxRotation
                }else if (rotate < minRotation){
                    rotate = minRotation
                }else{
                    rotate - rotate
                }



                invalidate()


            }

            MotionEvent.ACTION_UP ->{
                position = rotate

            }
        }



        return true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.rotate(rotate, width/2f, height/2f)

        paint.color = Color.BLACK
        paint2.color = Color.RED

       canvas?.drawCircle(width/2.0f, height/2f, 350f, paint)
        canvas?.drawCircle(width/3.0f, height/2.0f, 50f, paint2)




    }
}