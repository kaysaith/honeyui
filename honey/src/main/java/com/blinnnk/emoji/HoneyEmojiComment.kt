package com.blinnnk.emoji

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import com.blinnnk.uikit.HoneyColor

/**
 * @date 04/11/2017 14: 11 PM
 * @author Rita
 */

fun loadCommentEmoji(canvas: Canvas,
                  currentTime: Float,
                  duration: Float,
                  size: Float,
                  isAnimate: Boolean){

  val proceed = (currentTime / duration) % 1f
  val keyTimes = floatArrayOf(0f, 0.1f, 0.24f, 0.26F, 0.36f, 1f)

  val maxRotation = 2f
  var rotation = 0f

  if (isAnimate) {
    if (proceed >= keyTimes[1] && proceed < keyTimes[2]) {
      val currentPercent = ((proceed - keyTimes[1]) / (keyTimes[2] - keyTimes[1])).toDouble()
      rotation = 1f + Math.sin(Math.PI / 4 * currentPercent).toFloat() * maxRotation
    } else if (proceed >= keyTimes[2] && proceed < keyTimes[3]) {
      rotation = maxRotation
    } else if (proceed >= keyTimes[3] && proceed < keyTimes[4]) {
      val currentPercent = (proceed - keyTimes[3]) / (keyTimes[4] - keyTimes[3])
      rotation = maxRotation - currentPercent * maxRotation
    }
  }

  drawCommentEmoji(canvas, size, rotation)
}

/**
 * @date 04/11/2017
 * @author Rita
 * @description emoji Comment 带动画的评论icon
 * @param [canvas] 画布  [emojiSize] emoji直径  [rotation] 旋转的角度
 */
private fun drawCommentEmoji(canvas: Canvas, emojiSize: Float, rotation: Float) {

  val backgroundPaint = EmojiPaint.solidPaint(HoneyColor.Blue)
  val backgroundRect = RectF(0f, 0f, emojiSize, emojiSize)
  canvas.drawOval(backgroundRect, backgroundPaint)

  canvas.save()
  drawCommentSymbol(canvas, emojiSize, rotation)
  canvas.restore()

}

private fun drawCommentSymbol(canvas: Canvas, size: Float, rotation: Float) {


  val paint = EmojiPaint.solidPaint(Color.WHITE)
  val path = Path()
  val marginY = size * 0.24f
  val width = size * 0.7f
  val height = size * 0.5f
  val radius = size * 0.1f
  val rectangleRect = RectF((size - width) / 2f, marginY, (size + width) / 2f, height + marginY)
  path.addRoundRect(rectangleRect, radius, radius, Path.Direction.CW)
  path.moveTo(size * 0.61f, size * 0.71f)
  path.lineTo(size * 0.4f, size * 0.85f)
  path.lineTo(size * 0.4f, size * 0.73f)
  path.close()

  canvas.save()
  canvas.rotate(rotation, size / 2, size / 2)
  canvas.drawPath(path, paint)
  canvas.restore()
}