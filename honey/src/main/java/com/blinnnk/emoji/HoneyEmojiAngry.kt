@file:Suppress("NAME_SHADOWING")

package com.blinnnk.emoji

import android.graphics.*

/**
 * @date 24/10/2017
 * @author Rita
 */

fun loadAngryEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  marginX: Float,
  marginY: Float,
  size: Float,
  isAnimate: Boolean) {
  /// The key point in time
  val mainKeyTimes = floatArrayOf(0f, 0.4f, 0.47f, 0.6f, 0.66f, 0.71f, 0.76f, 0.8f, 1f)
  val eyeScaleKeyTimes = floatArrayOf(0f, 0.2f, 0.225f, 0.25f, 0.7f, 0.725f, 0.75f, 1f)
  val proceed: Float = currentTime / duration % 1f

  val faceOffsetUpY = -size * 0.2f
  val faceOffsetDownY = size * 0.08f
  val faceOffsetMaxX = size * 0.03f
  val eyeAnimateScaleMaxRate = 0.1f
  val browAnimateScaleYMaxRate = 0.3f
  val browAnimateScaleXMaxRate = 0.1f
  val mouthAnimateArcNormalRate = 0.85f
  val mouthAnimateArcMinRate = 0.6f
  val angrySymbolAnimateMaxRotate = 20f

  var faceOffsetY = 0f
  var faceOffsetX = 0f
  var eyeAnimateScaleRate = 1f
  var leftBrowShakeAnimateScaleRate = 1f
  var rightBrowShakeAnimateScaleRate = 1f
  var browUpAnimateScaleRate = 1f
  var mouthAnimateArcRate = mouthAnimateArcNormalRate
  var angrySymbolAnimateScaleRate = 0f
  var angrySymbolAnimateRotate = 0f

  if (isAnimate) {

    // The main animation, here includes: 1. The overall face displacement change 2.
    // The size of the eyebrow changes 3. Changes in the curvature of the mouth
    if (proceed < mainKeyTimes[1]) {
      // [Start ~ 1] is between 0 and 0.4f, Up
      val currentPercent = proceed / mainKeyTimes[1]
      faceOffsetY = faceOffsetUpY * currentPercent

      browUpAnimateScaleRate = 1f - browAnimateScaleYMaxRate * currentPercent
      leftBrowShakeAnimateScaleRate = browUpAnimateScaleRate
      rightBrowShakeAnimateScaleRate = browUpAnimateScaleRate

      mouthAnimateArcRate = mouthAnimateArcNormalRate - (mouthAnimateArcNormalRate - mouthAnimateArcMinRate) * currentPercent

    } else {

      // Changes in eyebrows and overall face position
      if (proceed < mainKeyTimes[3]) {
        // [1 ~ 2] 0.4f to 0.6f between
        val currentPercent = (proceed - mainKeyTimes[1]) / (mainKeyTimes[3] - mainKeyTimes[1])
        faceOffsetY = faceOffsetUpY + (faceOffsetDownY - faceOffsetUpY) * currentPercent

        browUpAnimateScaleRate = 1f - browAnimateScaleYMaxRate + browAnimateScaleYMaxRate * currentPercent
        leftBrowShakeAnimateScaleRate = browUpAnimateScaleRate
        rightBrowShakeAnimateScaleRate = browUpAnimateScaleRate

        // Angry symbol rotation angle changes
        if (proceed > mainKeyTimes[2]) {
          val currentPercent = (proceed - mainKeyTimes[2]) / (mainKeyTimes[3] - mainKeyTimes[2])
          angrySymbolAnimateScaleRate = currentPercent

        }


      } else if (proceed < mainKeyTimes[4]) {
        // [2 ~ 3] 0.6f to 0.66f between
        val currentPercent = (proceed - mainKeyTimes[3]) / (mainKeyTimes[4] - mainKeyTimes[3])
        faceOffsetY = faceOffsetDownY
        faceOffsetX = -faceOffsetMaxX * currentPercent
        leftBrowShakeAnimateScaleRate = 1f - browAnimateScaleXMaxRate * currentPercent
        rightBrowShakeAnimateScaleRate = 1f + browAnimateScaleXMaxRate * currentPercent

        angrySymbolAnimateScaleRate = 1f

      } else if (proceed < mainKeyTimes[6]) {
        // [3 ~ 5] between 0.66f and 0.76f
        val currentPercent = (proceed - mainKeyTimes[4]) / (mainKeyTimes[6] - mainKeyTimes[4])
        faceOffsetY = faceOffsetDownY
        faceOffsetX = -faceOffsetMaxX + 2 * faceOffsetMaxX * currentPercent

        leftBrowShakeAnimateScaleRate = 1f + browAnimateScaleXMaxRate * (-1f + 2f * currentPercent)
        rightBrowShakeAnimateScaleRate = 1f + browAnimateScaleXMaxRate * (1f - 2f * currentPercent)

        angrySymbolAnimateScaleRate = 1f

      } else if (proceed < mainKeyTimes[7]) {
        // [5 ~ 6] between 0.76f and 0.8f
        val invertedPercent = (mainKeyTimes[7] - proceed) / (mainKeyTimes[7] - mainKeyTimes[6])
        faceOffsetY = faceOffsetDownY
        faceOffsetX = faceOffsetMaxX * invertedPercent

        leftBrowShakeAnimateScaleRate = 1f + browAnimateScaleXMaxRate * invertedPercent
        rightBrowShakeAnimateScaleRate = 1f - browAnimateScaleXMaxRate * invertedPercent

        angrySymbolAnimateScaleRate = 1f

      } else {
        // [6 ~ end] 0.8f to 1f between
        val invertedPercent = (1f - proceed) / (1f - mainKeyTimes[7])
        faceOffsetY = faceOffsetDownY * invertedPercent

        angrySymbolAnimateScaleRate = invertedPercent

      }

      // Mouth angle changes
      if (proceed < mainKeyTimes[5]) {
        // [1 ~ 4] 0.4f to 0.76f between
        val currentPercent = (proceed - mainKeyTimes[1]) / (mainKeyTimes[5] - mainKeyTimes[1])
        mouthAnimateArcRate = mouthAnimateArcMinRate + (1f - mouthAnimateArcMinRate) * currentPercent

        angrySymbolAnimateRotate = angrySymbolAnimateMaxRotate * currentPercent

      } else {
        val currentPercent = (proceed - mainKeyTimes[5]) / (1f - mainKeyTimes[5])
        mouthAnimateArcRate = 1f - (1f - mouthAnimateArcNormalRate) * currentPercent
        angrySymbolAnimateRotate = angrySymbolAnimateMaxRotate * (1f - currentPercent)

      }
    }

    // Animated winking eyes
    if (proceed >= eyeScaleKeyTimes[1] && proceed < eyeScaleKeyTimes[2]) {
      // Smaller
      val currentPercent = (proceed - eyeScaleKeyTimes[1]) / (eyeScaleKeyTimes[2] - eyeScaleKeyTimes[1])
      eyeAnimateScaleRate = 1f - (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else if (proceed >= eyeScaleKeyTimes[2] && proceed < eyeScaleKeyTimes[3]) {
      // restore
      val currentPercent = (proceed - eyeScaleKeyTimes[2]) / (eyeScaleKeyTimes[3] - eyeScaleKeyTimes[2])
      eyeAnimateScaleRate = eyeAnimateScaleMaxRate + (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else if (proceed > eyeScaleKeyTimes[4] && proceed < eyeScaleKeyTimes[5]) {
      // Smaller
      val currentPercent = (proceed - eyeScaleKeyTimes[4]) / (eyeScaleKeyTimes[5] - eyeScaleKeyTimes[4])
      eyeAnimateScaleRate = 1f - (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else if (proceed > eyeScaleKeyTimes[5] && proceed < eyeScaleKeyTimes[6]) {
      // restore
      val currentPercent = (proceed - eyeScaleKeyTimes[5]) / (eyeScaleKeyTimes[6] - eyeScaleKeyTimes[5])
      eyeAnimateScaleRate = eyeAnimateScaleMaxRate + (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else {
      eyeAnimateScaleRate = 1f
    }
  }

  canvas.save()
  canvas.translate(marginX, marginY)
  drawAngryEmoji(canvas, size, faceOffsetX, faceOffsetY,
    eyeAnimateScaleRate,
    leftBrowShakeAnimateScaleRate, rightBrowShakeAnimateScaleRate, browUpAnimateScaleRate,
    mouthAnimateArcRate,
    angrySymbolAnimateScaleRate, angrySymbolAnimateRotate
  )
  canvas.restore()
}

private fun drawAngryEmoji(
  canvas: Canvas,
  emojiSize: Float,
  faceOffsetX: Float,
  faceOffsetY: Float,
  eyeAnimateScaleRate: Float,
  leftBrowShakeAnimateScaleRate: Float,
  rightBrowShakeAnimateScaleRate: Float,
  browUpAnimateScaleRate: Float,
  mouthAnimateArcRate: Float,
  angrySymbolAnimateScaleRate: Float,
  angrySymbolAnimateRotate: Float) {

  // Mouth parameters
  val mouthArc = 120f * mouthAnimateArcRate
  val mouthRadius = emojiSize * 0.14f
  val mouthMarginY = emojiSize * 0.62f

  // Eye parameters
  val eyeRadius = emojiSize * 0.075f
  val eyeLeftCenterX = emojiSize * 0.28f
  val eyeRightCenterX = emojiSize * 0.72f
  val eyeCenterY = emojiSize * 0.5f

  // Eyebrow parameters
  val browStartY = emojiSize * 0.47f
  val browHeight = emojiSize * 0.1f
  val browStartX = emojiSize * 0.38f
  val browWidth = emojiSize * 0.24f
  val leftBrowEndX = browStartX - browWidth * leftBrowShakeAnimateScaleRate
  val rightBrowEndX = emojiSize - browStartX + browWidth * rightBrowShakeAnimateScaleRate
  val browEndY = browStartY - browHeight * browUpAnimateScaleRate

  val angrySymbolSize = emojiSize * 0.25f * angrySymbolAnimateScaleRate
  val angrySymbolCenterX = emojiSize * 0.83f
  val angrySymbolCenterY = emojiSize * 0.225f

  // Prepare the coordinates of the mouth and eyes
  val mouthRect = RectF(emojiSize / 2 - mouthRadius,
    mouthMarginY,
    emojiSize / 2 + mouthRadius,
    mouthMarginY + mouthRadius * 2
  )
  val eyeLeftRect = RectF(eyeLeftCenterX - eyeRadius,
    eyeCenterY - eyeRadius * eyeAnimateScaleRate, eyeLeftCenterX + eyeRadius,
    eyeCenterY + eyeRadius * eyeAnimateScaleRate
  )
  val eyeRightRect = RectF(eyeRightCenterX - eyeRadius,
    eyeCenterY - eyeRadius * eyeAnimateScaleRate, eyeRightCenterX + eyeRadius,
    eyeCenterY + eyeRadius * eyeAnimateScaleRate
  )

  // Start drawing
  val solidPaint = EmojiPaint.solidPaint(EmojiUI.lineColor)
  val linePaint = EmojiPaint.emojiStrokePaint(emojiSize)

  // Draw the gradient of the color of the face
  canvas.save()
  val faceGradientColors = intArrayOf(Color.rgb(245, 62, 62),
    EmojiUI.faceColor,
    EmojiUI.faceColor
  )

  val faceGradientPositions = floatArrayOf(0f, emojiSize * 0.5f, emojiSize)
  EmojiPaint.drawGradientFace(canvas, faceGradientColors, faceGradientPositions, emojiSize)

  // Face up and down animation
  canvas.translate(faceOffsetX, faceOffsetY)

  // Draw the eye
  canvas.drawOval(eyeLeftRect, solidPaint)
  canvas.drawOval(eyeRightRect, solidPaint)

  // Draw eyebrows
  canvas.drawLine(browStartX, browStartY, leftBrowEndX, browEndY, linePaint)
  canvas.drawLine(emojiSize - browStartX, browStartY, rightBrowEndX, browEndY, linePaint)

  // Draw the mouth
  canvas.drawArc(mouthRect, 180f + (180f - mouthArc) / 2, mouthArc, false, linePaint)

  // Draw angry logo
  drawEmojiAngrySymbol(canvas, angrySymbolSize, angrySymbolCenterX - angrySymbolSize / 2,
    angrySymbolCenterY - angrySymbolSize / 2, angrySymbolAnimateRotate
  )

  canvas.restore()

}

fun drawPureFace(canvas: Canvas, emojiSize: Float) {

  val paint = Paint()
  paint.reset()
  paint.isAntiAlias = true
  paint.color = EmojiUI.faceColor
  paint.style = Paint.Style.FILL
  canvas.drawCircle(emojiSize / 2, emojiSize / 2, emojiSize / 2, paint)

}

private fun drawEmojiAngrySymbol(
  canvas: Canvas,
  size: Float,
  marginX: Float,
  marginY: Float,
  rotate: Float) {

  val path = emojiAngryPath(size)
  val paint = Paint()
  paint.reset()
  paint.isAntiAlias = true
  paint.strokeWidth = size * 0.2f
  paint.color = EmojiUI.lineColor
  paint.strokeCap = Paint.Cap.ROUND
  paint.style = Paint.Style.STROKE

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotate, size / 2f, size / 2f)
  canvas.drawPath(path, paint)
  canvas.restore()

}

private fun emojiAngryPath(size: Float): Path {
  val path = Path()
  val halfSize = size / 2f
  val halfMargin = size * 0.12f
  val controlMargin = size * 0.1f
  val lineWidth = size * 0.2f

  val a1X: Float = halfSize - halfMargin
  val a1y: Float = lineWidth / 2f
  val a2x: Float = lineWidth / 2f
  val a2y: Float = halfSize - halfMargin

  val b1x: Float = lineWidth / 2f
  val b1y: Float = halfSize + halfMargin
  val b2x: Float = halfSize - halfMargin
  val b2y: Float = size - lineWidth / 2f

  val c1x: Float = halfSize + halfMargin
  val c1y: Float = size - lineWidth / 2f
  val c2x: Float = size - lineWidth / 2f
  val c2y: Float = halfSize + halfMargin

  val d1x: Float = size - lineWidth / 2f
  val d1y: Float = halfSize - halfMargin
  val d2x: Float = halfSize + halfMargin
  val d2y: Float = lineWidth / 2f

  path.moveTo(a1X, a1y)
  path.quadTo(halfSize - controlMargin, halfSize - controlMargin, a2x, a2y)

  path.moveTo(b1x, b1y)
  path.quadTo(halfSize - controlMargin, halfSize + controlMargin, b2x, b2y)

  path.moveTo(c1x, c1y)
  path.quadTo(halfSize + controlMargin, halfSize + controlMargin, c2x, c2y)

  path.moveTo(d1x, d1y)
  path.quadTo(halfSize + controlMargin, halfSize - controlMargin, d2x, d2y)

  return path
}