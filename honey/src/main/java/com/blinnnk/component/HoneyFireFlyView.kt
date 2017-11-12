package com.blinnnk.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import com.blinnnk.uikit.HoneyColor
import com.blinnnk.uikit.Position
import com.blinnnk.uikit.ScreenSize
import com.blinnnk.uikit.matchParentViewGroup

class HoneyFireFlyView(context: Context) : View(context) {

  private val paint = Paint()
  private val circlePositionList = listOf(
    Position(circleDiameter, ScreenSize.Height * 0.1f),
    Position(ScreenSize.Width * 0.92f, ScreenSize.Height * 0.26f),
    Position(ScreenSize.Width * 0.95f, ScreenSize.Height * 0.12f),
    Position(ScreenSize.Width * 0.78f, ScreenSize.Height / 2f),
    Position(ScreenSize.Width * 0.12f, ScreenSize.Height / 2f)
  )
  private var angel = 0.0
  private var scaleSize = 0f
  private var isToBig = true
  private val scaleSpeed = 0.16f
  private val paintColor = listOf(
    HoneyColor.Yellow,
    HoneyColor.Red,
    HoneyColor.Blue,
    HoneyColor.Yellow,
    HoneyColor.Red
  )

  init {

    isClickable = false
    layoutParams = ViewGroup.LayoutParams(matchParentViewGroup, matchParentViewGroup)

    paint.color = HoneyColor.Yellow
    paint.style = Paint.Style.FILL
    paint.isAntiAlias = true

  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    (0 until circlePositionList.count()).forEach {

      val animationOffsetX = 200 * Math.cos(angel).toFloat()
      val animationOffsetY = 50f * Math.sin(angel).toFloat()
      val offsetList = listOf(animationOffsetX, animationOffsetY)
      paint.color = paintColor[it]
      canvas?.drawCircle(
        circlePositionList[it].left + offsetList[it % 2],
        circlePositionList[it].top + offsetList[(it + 1) % 2],
        circleDiameter + scaleSize,
        paint
      )

    }

    angel += 0.008f

    if (scaleSize < circleRadius && isToBig) {
      scaleSize += scaleSpeed
    }
    if (scaleSize >= circleRadius) {
      isToBig = false
    }
    if (!isToBig) {
      scaleSize -= scaleSpeed
      if (scaleSize <= -circleRadius) {
        isToBig = true
      }
    }

    postInvalidateDelayed(10)

  }

  companion object {
    private val circleRadius = 10f
    private val circleDiameter = circleRadius * 2
  }

}