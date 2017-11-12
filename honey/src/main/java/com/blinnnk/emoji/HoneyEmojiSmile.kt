package com.blinnnk.emoji

import android.graphics.Canvas
import android.graphics.RectF
import com.blinnnk.util.angleToRadian

/**
 * @date 24/10/2017
 * @author Rita
 */

fun loadSmileEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  marginX: Float,
  marginY: Float,
  size: Float,
  isAnimate: Boolean) {
  var rotate = 90f
  if (isAnimate) {
    rotate = 90f - 360f * currentTime / duration
  }

  canvas.save()
  canvas.translate(marginX, marginY)
  drawSmileEmoji(canvas, size, rotate)
  canvas.restore()
}

private fun drawSmileEmoji(canvas: Canvas, emojiSize: Float, rotate: Float) {

  /** Take the face center as the axis, the maximum angle of rotation*/
  val smileFaceRotateMaxAngle = 20f

  // Axis of animation movement
  val animateRadius = emojiSize * 0.1f
  val animateCenterX = emojiSize / 2f   //动画运动的轴心-x
  val animateCenterY = emojiSize / 2f     //动画运动的轴心-y

  // Face parameters
  val eyeDiameter = emojiSize * 0.12f
  val mouthDiameter = emojiSize * 0.32f
  val cheekRadius = emojiSize * 0.225f  // 动画最大展示的红脸蛋大小

  val eyeMarginX = emojiSize * 0.2f
  val eyeMarginY = emojiSize * 0.33f
  val mouthMarginY = emojiSize * 0.33f
  val cheekCenterY = emojiSize * 0.53f
  val cheekLeftCenterX = emojiSize * 0.02f + cheekRadius
  val cheekRightCenterX = emojiSize * 0.98f - cheekRadius
  val cheekScaleRate = 0.9f

  // Eye position
  val leftEyeRect = RectF(eyeMarginX, eyeMarginY, eyeMarginX + eyeDiameter,
    eyeMarginY + eyeDiameter
  )
  val rightEyeRect = RectF(emojiSize - eyeMarginX - eyeDiameter, eyeMarginY, emojiSize - eyeMarginX,
    eyeMarginY + eyeDiameter
  )

  // Mouth position
  val mouthRect = RectF((emojiSize - mouthDiameter) / 2f, mouthMarginY,
    (emojiSize + mouthDiameter) / 2f, mouthMarginY + mouthDiameter
  )

  // Eye and mouth rotate around faceRotate
  val rotateAngle = smileFaceRotateMaxAngle * Math.cos(angleToRadian(rotate)).toFloat()

  // The whole face swings the only parameter
  val translateX = Math.cos(angleToRadian(rotate)).toFloat() * animateRadius
  val translateY = Math.abs(Math.sin(angleToRadian(rotate)).toFloat()) * -animateRadius

  // Cheek conversion scale parameters
  val leftCheekRadius = cheekRadius * (cheekScaleRate + Math.cos(
    angleToRadian(rotate)
  ).toFloat() * (1f - cheekScaleRate))
  val rightCheekRadius = cheekRadius * (cheekScaleRate - Math.cos(
    angleToRadian(rotate)
  ).toFloat() * (1f - cheekScaleRate))

  // Draw the face
  canvas.save()
  drawPureFace(canvas, emojiSize)

  // Rotate face animation
  canvas.rotate(rotateAngle, animateCenterX, animateCenterY)
  canvas.translate(translateX, translateY)

  drawGradientCheekLayer(canvas, cheekLeftCenterX, cheekCenterY, leftCheekRadius)
  drawGradientCheekLayer(canvas, cheekRightCenterX, cheekCenterY, rightCheekRadius)

  val paint = EmojiPaint.emojiStrokePaint(emojiSize)

  canvas.drawArc(leftEyeRect, 180f, 180f, false, paint)
  canvas.drawArc(rightEyeRect, 180f, 180f, false, paint)
  canvas.drawArc(mouthRect, 30f, 120f, false, paint)
  canvas.restore()

}

private fun drawGradientCheekLayer(
  canvas: Canvas,
  centerX: Float,
  centerY: Float,
  radius: Float) {

  val paint = EmojiPaint.emojiCheekPaint(centerX, centerY, radius)
  canvas.drawCircle(centerX, centerY, radius, paint)

}