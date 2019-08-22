package com.example.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class circle2 (context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()


    override fun onDraw(canvas: Canvas?) {
        paint.color= Color.BLUE
        canvas?.drawCircle(width/2.0f,height/2f,75f,paint)
        super.onDraw(canvas)
    }
}