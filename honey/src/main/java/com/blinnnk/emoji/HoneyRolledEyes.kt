package com.blinnnk.emoji

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

/**
 * @date 24/10/2017
 * @author Rita
 */

fun loadRolledEyeEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  marginX: Float,
  marginY: Float,
  size: Float,
  isAnimate: Boolean) {
  /// 关键时间点
  val proceed: Float = currentTime / duration % 1f
  val eyeKeyTimes = floatArrayOf(0f, 0.25f, 0.5f, 0.65f, 0.8f, 1f)

  var faceRotateRate = 1f
  var eyeballOffsetRate = 1f

  if (isAnimate) {
    // 脸部运动
    faceRotateRate = if (proceed < 0.5f) {
      val currentPercent = proceed / (0.5f)
      1f - currentPercent
    } else {
      val currentPercent = (proceed - 0.5f) / (0.5f)
      currentPercent
    }

    // 眼睛运动
    if (proceed >= eyeKeyTimes[1] && proceed < eyeKeyTimes[2]) {
      val currentPercent = (proceed - eyeKeyTimes[1]) / (eyeKeyTimes[2] - eyeKeyTimes[1])
      eyeballOffsetRate = 1f - currentPercent
    } else if (proceed >= eyeKeyTimes[2] && proceed < eyeKeyTimes[3]) {
      eyeballOffsetRate = 0f
    } else if (proceed >= eyeKeyTimes[3] && proceed < eyeKeyTimes[4]) {
      val currentPercent = (proceed - eyeKeyTimes[3]) / (eyeKeyTimes[4] - eyeKeyTimes[3])
      eyeballOffsetRate = currentPercent
    }
  }

  canvas.save()
  canvas.translate(marginX, marginY)
  drawRolledEyeEmoji(canvas,
    size,
    faceRotateRate,
    eyeballOffsetRate
  )
  canvas.restore()
}

/** emoji RolledEye 翻白眼
 * Created by Rita on 2017/9/25.
 * <h5> emoji RolledEye 翻白眼表情动画</h5>
 * ---------------------------------------------
 * @param [canvas] 画布 [emojiSize] <Float> emoji直径 [faceRotateRate] <Float> 整个脸的旋转程度，
 * 控制脸部五官的上下运动，取值范围: [0-1] [eyeballOffsetRate] <Float> 翻白眼眼球的偏移程度，取值范围：[0-1]
 */
private fun drawRolledEyeEmoji(
  canvas: Canvas,
  emojiSize: Float,
  faceRotateRate: Float,
  eyeballOffsetRate: Float) {

  // 嘴巴的参数
  val mouthWidth = emojiSize * 0.16f
  val mouthMarginY = emojiSize * 0.75f

  // 眼睛的参数
  val eyeRadius = emojiSize * 0.15f
  val eyeballRadius = emojiSize * 0.06f
  val eyeLeftCenterX = emojiSize * 0.26f
  val eyeRightCenterX = emojiSize - eyeLeftCenterX
  val eyeCenterY = emojiSize * 0.5f

  // 动画参数
  val eyeBallOffsetMaxY = -(eyeRadius - eyeballRadius)
  val eyeBallOffsetY = eyeBallOffsetMaxY * eyeballOffsetRate
  val faceMaxRotate = -3.6f
  val faceRotate = faceMaxRotate * faceRotateRate

  // 准备嘴巴和眼睛的坐标
  val eyeLeftRect = RectF(eyeLeftCenterX - eyeRadius,
    eyeCenterY - eyeRadius,
    eyeLeftCenterX + eyeRadius,
    eyeCenterY + eyeRadius
  )
  val eyeRightRect = RectF(eyeRightCenterX - eyeRadius,
    eyeCenterY - eyeRadius,
    eyeRightCenterX + eyeRadius,
    eyeCenterY + eyeRadius
  )
  val eyeballLeftRect = RectF(eyeLeftCenterX - eyeballRadius,
    eyeCenterY - eyeballRadius + eyeBallOffsetY,
    eyeLeftCenterX + eyeballRadius,
    eyeCenterY + eyeballRadius + eyeBallOffsetY
  )
  val eyeballRightRect = RectF(eyeRightCenterX - eyeballRadius,
    eyeCenterY - eyeballRadius + eyeBallOffsetY,
    eyeRightCenterX + eyeballRadius,
    eyeCenterY + eyeballRadius + eyeBallOffsetY
  )

  // 开始绘制
  val eyePaint = EmojiPaint.solidPaint(Color.WHITE)
  val eyeballPaint = EmojiPaint.solidPaint(EmojiUI.lineColor)
  val linePaint = EmojiPaint.emojiStrokePaint(emojiSize)

  // 绘制渐变底色的脸
  canvas.save()
  drawPureFace(canvas, emojiSize)

  // 脸部上下运动的动画
  canvas.rotate(faceRotate, emojiSize, emojiSize / 2f)

  // 绘制眼睛
  canvas.drawOval(eyeLeftRect, eyePaint)
  canvas.drawOval(eyeRightRect, eyePaint)
  canvas.drawOval(eyeballLeftRect, eyeballPaint)
  canvas.drawOval(eyeballRightRect, eyeballPaint)

  // 绘制嘴巴
  canvas.drawLine((emojiSize - mouthWidth) / 2f, mouthMarginY, (emojiSize + mouthWidth) / 2f,
    mouthMarginY, linePaint
  )

  canvas.restore()

}