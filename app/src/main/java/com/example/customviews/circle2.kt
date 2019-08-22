package com.example.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent



class circle2 (context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private var startX : Float = 0f
    private var startY : Float = 0f
    private var diffX : Float = 0f
    private var diffY : Float =0f


    override fun onDraw(canvas: Canvas?) {
        paint.color= Color.BLUE
        canvas?.drawCircle(width/2.0f +diffX,height/2f +diffY,75f,paint)
        super.onDraw(canvas)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean { // triggered each time the touch state changes
        when (event.action) {
            MotionEvent.ACTION_DOWN // triggered when view is touched
            -> { startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE // triggered after ACTION_DOWN but when touch is moved
            ->
                // get end point and calculate total distance traveled
                // use the total distance traveled to calculate the desired change in rotation
                // apply that change to your rotation variable
                // you may want to use a minimum and maximum rotation value to limit the rotation
                // use the new rotation to convert to the desired volume setting
                invalidate() // this will cause the onDraw method to be called again with your new values
            MotionEvent.ACTION_UP // triggered when touch ends
            -> { diffX = event.x - startX
                diffY = event.y - startY
            }
        }// get and store start point with event.getX()
        invalidate()
        return true // this indicates that the event has been processed
    }
}