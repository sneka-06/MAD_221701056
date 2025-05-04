package com.example.graphics


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class SampleCanvas @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Create a Paint object for setting drawing styles
        val paint = Paint()
        paint.style = Paint.Style.FILL // Fill the shapes

        // Draw a Circle
        paint.color = Color.BLUE
        canvas.drawCircle(300f, 300f, 100f, paint)

        // Draw an Ellipse
        paint.color = Color.GREEN
        canvas.drawOval(RectF(100f, 500f, 500f, 700f), paint)

        // Draw a Rectangle
        paint.color = Color.RED
        canvas.drawRect(100f, 800f, 500f, 1000f, paint)

        // Draw some text
        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText("Hello, Android!", 150f, 1200f, paint)
    }
}
