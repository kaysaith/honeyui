package com.blinnnk.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.widget.LinearLayout
import com.blinnnk.uikit.GradientStyle
import com.blinnnk.util.HoneyPaintUtil
import com.blinnnk.util.observing

class HoneyLoadingRingView(
  context: Context,
  private val circleSize: Float,
  private val style: GradientStyle ,
  private val radius: Float
) : View(context) {

  var isLoading: Boolean? by observing(null, didSet = {
    this.invalidate()
  })

  private var animateAngle = 0.8f
  private var speedTime: Long = 10

  private val paint = Paint()
  private val ringPaint = Paint()
  private val count = 24
  private val unitAngle = 360f / count
  private var nowTime: Long = 0
  private val duration = 2 * unitAngle / animateAngle * speedTime

  init {
    layoutParams = LinearLayout.LayoutParams(circleSize.toInt(), circleSize.toInt())
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (isLoading == true) {
      nowTime += speedTime
      (0 until count).forEach {
        //第 n 个线段延迟 index / count 的时间
        val delay = duration * it / count
        val drawRadius: Float
        val tatterAngle: Float
        val sweepAngle: Float

        //实际执行动画的时长
        val time = (nowTime + duration - delay) % duration
        if (time / duration < 0.5) {
          drawRadius = radius
          tatterAngle = 0f
          sweepAngle = time / speedTime * animateAngle
        } else {
          tatterAngle = (time - duration / 2) / speedTime * animateAngle
          sweepAngle = unitAngle - tatterAngle
          drawRadius = radius - tatterAngle / 4
        }
        val startAngle = tatterAngle + it * unitAngle
        drawProgressRing(canvas, circleSize, startAngle, sweepAngle, drawRadius, style)
      }
      postInvalidateDelayed(speedTime)

    } else {
      drawCircle(canvas, circleSize, radius, style)
    }
  }

  private fun drawProgressRing(canvas: Canvas, circleSize: Float, startAngel: Float, endAngle: Float, radius: Float, style: GradientStyle) {
    val rect = RectF(radius / 2, radius / 2, circleSize - radius, circleSize - radius)
    ringPaint.isAntiAlias = true
    ringPaint.strokeWidth = radius
    ringPaint.style = Paint.Style.STROKE
    ringPaint.strokeCap = Paint.Cap.ROUND
    ringPaint.shader = HoneyPaintUtil.honeyLinearGradient(height.toFloat(), style)
    canvas.drawArc(rect, startAngel, endAngle, false, ringPaint)
  }

  private fun drawCircle(canvas: Canvas, circleSize: Float, radius: Float, style: GradientStyle) {
    paint.reset()
    paint.isAntiAlias = true
    paint.strokeWidth = radius
    paint.style = Paint.Style.STROKE
    paint.shader = HoneyPaintUtil.honeyLinearGradient(height.toFloat(), style)
    canvas.drawCircle(width / 2f, height / 2f, (circleSize - radius) / 2, paint)
  }

}