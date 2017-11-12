package com.blinnnk.emoji

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.widget.LinearLayout
import com.blinnnk.uikit.ScreenSize
import com.blinnnk.util.observing
import com.blinnnk.uikit.uiPX

/**
 * @date 08/09/2017.
 * @author Rita
 */

enum class EmojiType {
  Smile, Thumb, RolledEye, Sob, Angry, Speechless, Like, Comment
}

@SuppressLint("ViewConstructor")
class HoneyEmojiButton(context: Context, private val type: EmojiType) : View(context) {

  private val unitMargin = (ScreenSize.Width - 55.uiPX() * 4 - 15.uiPX() * 4) / 8
  private val iconSize = 55.uiPX().toFloat()

  init {
    layoutParams = LinearLayout.LayoutParams(iconSize.toInt(), iconSize.toInt()).apply {
      leftMargin = unitMargin
      rightMargin = unitMargin
    }
  }

  private var isAnimate: Boolean by observing(true) {
    invalidate()
  }

  /** 动画刷新时间*/
  private var speedTime: Float = EmojiUI.speedTime
  private var duration: Float = EmojiUI.duration
  private var currentTime: Float = 0f

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    when (type) {
      EmojiType.Angry ->
        loadAngryEmoji(canvas, currentTime, duration, 0f, 0f, iconSize, isAnimate)
      EmojiType.Smile ->
        loadSmileEmoji(canvas, currentTime, duration, 0f, 0f, iconSize, isAnimate)
      EmojiType.Sob ->
        loadSobEmoji(canvas, currentTime, duration, 0f, 0f, iconSize, isAnimate)
      EmojiType.Thumb ->
        loadThumbEmoji(canvas, currentTime, duration, 0f, 0f, iconSize, isAnimate)
      EmojiType.RolledEye ->
        loadRolledEyeEmoji(canvas, currentTime, duration, 0f, 0f, iconSize, isAnimate)
      EmojiType.Speechless ->
        loadSpeechlessEmoji(canvas, currentTime, duration, 0f, 0f, iconSize, isAnimate)
      EmojiType.Like ->
        loadLikeEmoji(canvas, currentTime, duration, iconSize, isAnimate)
      EmojiType.Comment ->
        loadCommentEmoji(canvas, currentTime, duration, iconSize, isAnimate)
    }

    currentTime += speedTime

    postInvalidateDelayed(speedTime.toLong())
  }

  fun playAnimation(bool: Boolean) {
    isAnimate = bool
  }

}
