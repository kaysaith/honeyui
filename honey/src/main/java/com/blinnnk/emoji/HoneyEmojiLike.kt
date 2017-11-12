package com.blinnnk.emoji

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import com.blinnnk.uikit.HoneyColor

/**
 * @date 28/10/2017 9:02 PM
 * @author Rita
 */

fun loadLikeEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  size: Float,
  isAnimate: Boolean) {

  val proceed = (currentTime / duration) % 1f
  val keyTimes = floatArrayOf(0f, 0.1f, 0.24f, 0.26f, 0.36f, 1f)

  val scaleMaxRate = 1.1f
  var scaleRate = 1f

  if (isAnimate) {
    if (proceed >= keyTimes[1] && proceed < keyTimes[2]) {
      val currentPercent = ((proceed - keyTimes[1]) / (keyTimes[2] - keyTimes[1])).toDouble()
      scaleRate = 1f + Math.sin(Math.PI / 4 * currentPercent).toFloat() * (scaleMaxRate - 1f)
    } else if (proceed >= keyTimes[2] && proceed < keyTimes[3]) {
      scaleRate = scaleMaxRate
    } else if (proceed >= keyTimes[3] && proceed < keyTimes[4]) {
      val currentPercent = (proceed - keyTimes[3]) / (keyTimes[4] - keyTimes[3])
      scaleRate = scaleMaxRate - currentPercent * (scaleMaxRate - 1f)
    }
  }

  drawLikeEmoji(canvas, size, scaleRate)
}

private fun drawLikeEmoji(canvas: Canvas, emojiSize: Float, ScaleRate: Float) {

  val heartSize = emojiSize * 0.66f
  val margin = emojiSize * 0.17f

  val backgroundPaint = EmojiPaint.solidPaint(HoneyColor.Red)
  val rectF = RectF(0f, 0f, emojiSize, emojiSize)
  canvas.drawOval(rectF, backgroundPaint)

  canvas.save()
  canvas.scale(ScaleRate, ScaleRate, emojiSize / 2f, emojiSize / 2f)
  drawEmojiHeartSymbol(canvas, margin, margin, Color.WHITE, heartSize)
  canvas.restore()

}

fun drawEmojiHeartSymbol(
  canvas: Canvas,
  marginX: Float,
  marginY: Float,
  color: Int,
  size: Float) {
  val path = heartPath(size)
  val paint = EmojiPaint.solidPaint(color)

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.scale(1f, 0.94f, size / 2f, size / 2f)
  canvas.drawPath(path, paint)
  canvas.restore()
}

private fun heartPath(size: Float): Path {
  val path = Path()
  val AX = size * 0.5f
  val AY = size * 0.22f

  val BX = size * 0.73f
  val BY = size * 0.04f

  val CX = size
  val CY = size * 0.32f

  val DX = size * 0.81f
  val DY = size * 0.7f

  val EX = size * 0.5f
  val EY = size * 0.95f

  val FX = size - DX
  val FY = DY

  val IX = size - CX
  val IY = CY

  val GX = size - BX
  val GY = BY

  val ab1X = size * 0.55f
  val ab1Y = size * 0.09f
  val ab2X = size * 0.66f
  val ab2Y = size * 0.04f

  val bc1X = size * 0.85f
  val bc1Y = size * 0.04f
  val bc2X = size
  val bc2Y = size * 0.16f

  val cd1X = size
  val cd1Y = size * 0.47f
  val cd2X = size * 0.91f
  val cd2Y = size * 0.6f

  val de1X = size * 0.71f
  val de1Y = size * 0.81f
  val de2X = size * 0.55f
  val de2Y = size * 0.94f

  val ef1X = size - de2X
  val ef1Y = de2Y
  val ef2X = size - de1X
  val ef2Y = de1Y

  val fi1X = size - cd2X
  val fi1Y = cd2Y
  val fi2X = size - cd1X
  val fi2Y = cd1Y

  val ig1X = size - bc2X
  val ig1Y = bc2Y
  val ig2X = size - bc1X
  val ig2Y = bc1Y

  val ga1X = size - ab2X
  val ga1Y = ab2Y
  val ga2X = size - ab1X
  val ga2Y = ab1Y

  path.moveTo(AX, AY)
  path.cubicTo(ab1X, ab1Y, ab2X, ab2Y, BX, BY)
  path.cubicTo(bc1X, bc1Y, bc2X, bc2Y, CX, CY)
  path.cubicTo(cd1X, cd1Y, cd2X, cd2Y, DX, DY)
  path.cubicTo(de1X, de1Y, de2X, de2Y, EX, EY)
  path.cubicTo(ef1X, ef1Y, ef2X, ef2Y, FX, FY)
  path.cubicTo(fi1X, fi1Y, fi2X, fi2Y, IX, IY)
  path.cubicTo(ig1X, ig1Y, ig2X, ig2Y, GX, GY)
  path.cubicTo(ga1X, ga1Y, ga2X, ga2Y, AX, AY)

  return path
}
