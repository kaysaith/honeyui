package com.blinnnk.util

import android.graphics.PointF

fun calculateAngel(startPoint: PointF, x2: Float, y2: Float) =
  Math.abs(Math.toDegrees(Math.atan2((y2 - startPoint.y).toDouble(), (x2 - startPoint.x).toDouble())).toFloat())

fun angleToRadian(angle: Float): Double {
  return Math.PI * angle.toDouble() / 180
}