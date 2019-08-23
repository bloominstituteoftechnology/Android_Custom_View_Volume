package com.example.android_custom_view_volume.activity.activity.view


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.android_custom_view_volume.R


class CustomView(context: Context, attrs: AttributeSet?):
    LinearLayout(context, attrs){

    constructor(context: Context): this(context, null)


    init {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.customeview_layout, this, false) as ImageView

        view.setImageDrawable(ContextCompat
            .getDrawable(context, R.drawable.blackvolume))

        val redView = ImageView(context)
        redView.setImageDrawable(ContextCompat
            .getDrawable(context, R.drawable.smallredcircle))


        orientation = VERTICAL

        addView(view)
        addView(redView)

    }


}