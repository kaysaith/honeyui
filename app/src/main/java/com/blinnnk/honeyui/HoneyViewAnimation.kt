package com.blinnnk.honeyui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.view.animation.*
import android.widget.RelativeLayout

/**
 * @date 11/11/2017 8:23 PM
 * @author KaySaith
 */

fun View.zoomIn(duration: Long = AnimationDuration.Default, callback: () -> Unit = { }) {
  val animationSet = AnimationSet(false)
  val scale = ScaleAnimation(1.8f, 1f, 1.8f, 1f,
    ScreenSize.centerX, ScreenSize.centerY
  )
  val alpha = AlphaAnimation(0f, 1f)
  animationSet.addAnimation(scale)
  animationSet.addAnimation(alpha)
  animationSet.interpolator = OvershootInterpolator()
  animation.duration = duration
  animation = animationSet
  animation.startNow()
  // Callbacks are supported when the animation is finished
  animationSet.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationRepeat(p0: Animation?) {}
    override fun onAnimationEnd(p0: Animation?) {
      callback()
    }
  }
  )
}

fun View.zoomOut(duration: Long = AnimationDuration.Default, callback: () -> Unit = { }) {
  val animationSet = AnimationSet(false)
  val scale = ScaleAnimation(1f, 2f, 1f, 2f,
    ScreenSize.centerX, ScreenSize.centerY
  )
  val alpha = AlphaAnimation(1f, 0f)
  animationSet.addAnimation(scale)
  animationSet.addAnimation(alpha)
  scale.interpolator = OvershootInterpolator()
  alpha.interpolator = OvershootInterpolator()
  animationSet.duration = duration
  animation = animationSet
  animation.startNow()
  // Callbacks are supported when the animation is finished
  animationSet.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationRepeat(p0: Animation?) {}
    override fun onAnimationEnd(p0: Animation?) {
      callback()
    }
  }
  )
}

fun View.updateOriginXAnimation(finalLeft: Float, callback: () -> Unit) {
  val valueAnimator = ValueAnimator.ofFloat(x, finalLeft)
  valueAnimator.duration = AnimationDuration.Default
  valueAnimator.interpolator = AnticipateOvershootInterpolator()
  valueAnimator.addUpdateListener { animator ->
    val currentValue = animator.animatedValue as Float
    x = currentValue
    requestLayout()
  }
  valueAnimator.start()
  valueAnimator.addListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator?) {}
    override fun onAnimationCancel(p0: Animator?) {}
    override fun onAnimationEnd(p0: Animator?) {
      callback()
    }

    override fun onAnimationRepeat(p0: Animator?) {}
  })
}

fun View.updateOriginYAnimation(finalTop: Float, callback: () -> Unit = { }) {
  val valueAnimator = ValueAnimator.ofFloat(y, finalTop)
  valueAnimator.duration = AnimationDuration.Default
  valueAnimator.interpolator = AnticipateOvershootInterpolator()
  valueAnimator.addUpdateListener { animator ->
    val currentValue = animator.animatedValue as Float
    y = currentValue
    requestLayout()
  }
  valueAnimator.start()
  valueAnimator.addListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator?) {}
    override fun onAnimationCancel(p0: Animator?) {}
    override fun onAnimationRepeat(p0: Animator?) {}
    override fun onAnimationEnd(p0: Animator?) {
      callback()
    }
  })
}

fun View.updateAlphaAnimation(finalAlpha: Float, callback: () -> Unit = { }) {
  val valueAnimator = ValueAnimator.ofFloat(alpha, finalAlpha)
  valueAnimator.duration = AnimationDuration.Default
  valueAnimator.interpolator = AnticipateOvershootInterpolator()
  valueAnimator.addUpdateListener { animator ->
    val currentValue = animator.animatedValue as Float
    alpha = currentValue
    requestLayout()
  }
  valueAnimator.start()
  valueAnimator.addListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator?) {}
    override fun onAnimationCancel(p0: Animator?) {}
    override fun onAnimationRepeat(p0: Animator?) {}
    override fun onAnimationEnd(p0: Animator?) {
      callback()
    }
  })
}

fun View.fallOut(callback: () -> Unit = { }) {
  val animationSet = AnimationSet(false)
  val fallOut = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, 0,
    ScreenSize.Width * 3f,
    Animation.RELATIVE_TO_SELF, 0f, 0, 0f
  )
  val rotate = RotateAnimation(
    0f,
    30f,
    RotateAnimation.RELATIVE_TO_SELF,
    0.9F,
    RotateAnimation.RELATIVE_TO_SELF,
    0.1F
  )
  animationSet.addAnimation(fallOut)
  animationSet.addAnimation(rotate)
  animationSet.interpolator = OvershootInterpolator()
  fallOut.duration = AnimationDuration.Default
  rotate.duration = AnimationDuration.Default
  startAnimation(animationSet)
  animationSet.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationRepeat(p0: Animation?) {}
    override fun onAnimationEnd(p0: Animation?) {
      50L timeUpThen { callback() }
    }
  })
}

fun View.slideDown(callback: () -> Unit = { }) {
  val slideDown = TranslateAnimation(0f, 0f, -ScreenSize.centerY, 0f)
  slideDown.interpolator = OvershootInterpolator()
  slideDown.duration = AnimationDuration.Default
  startAnimation(slideDown)
  slideDown.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationEnd(p0: Animation?) { callback() }
    override fun onAnimationRepeat(p0: Animation?) {}
  })
}

fun View.swipeUp(callback: () -> Unit = { }) {
  val slideUp = TranslateAnimation(
    0f,
    0f,
    ScreenSize.Width.toFloat(),
    0f
  )
  slideUp.interpolator = AccelerateInterpolator()
  slideUp.duration = AnimationDuration.Default
  startAnimation(slideUp)
  slideUp.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationEnd(p0: Animation?) { callback() }
    override fun onAnimationRepeat(p0: Animation?) {}
  })
}


fun View.squeezeDown(callback: () -> Unit = { }) {
  val squeezeDown = TranslateAnimation(
    0f,
    0f,
    -ScreenSize.centerY,
    0f
  )
  squeezeDown.interpolator = AnticipateOvershootInterpolator()
  squeezeDown.duration = AnimationDuration.Default
  startAnimation(squeezeDown)
  squeezeDown.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {}
    override fun onAnimationEnd(p0: Animation?) {
      callback()
    }

    override fun onAnimationRepeat(p0: Animation?) {}
  })
}

/**
 * Set multiple int type property animation at one time
 */
fun View.setMultipleAnimationOfInt(
  duration: Long = AnimationDuration.Default,
  vararg parameters: ValueAnimationOfIntParameters
  ) {
  val animatorArray = arrayListOf<Animator>()
  val animatorSet = AnimatorSet()
  parameters.map { (animationIntObject, startValue, endValue) ->
    animatorArray.add(ValueAnimator.ofInt(startValue, endValue).apply {
      addUpdateListener { animator ->
        val currentValue = animator.animatedValue as Int
        when (animationIntObject) {
          AttributeAnimationType.Width -> layoutParams.width = currentValue
          AttributeAnimationType.PaddingTop -> setPadding(currentValue, currentValue, currentValue,
            currentValue
          )
          AttributeAnimationType.PaddingBottom -> bottomPadding = currentValue
          AttributeAnimationType.MarginLeft -> (layoutParams as? RelativeLayout.LayoutParams)?.leftMargin = currentValue
          AttributeAnimationType.MarginBottom -> (layoutParams as? RelativeLayout.LayoutParams)?.bottomMargin = currentValue
          else -> layoutParams.height = currentValue
        }
        requestLayout()
      }
    }
    )
  }
  animatorSet.playTogether(animatorArray)
  animatorSet.duration = duration
  animatorSet.interpolator = AccelerateDecelerateInterpolator()
  animatorSet.start()
}

/**
 * `ObjectAnimator` to set infinite parameters, simplify multi-animation calling,
 * and optimize the number of codes.
 * [callBack] Callbacks are supported when the animation is finished
 */

inline fun View.multipleParametersObjectAnimator(
  vararg value: ObjectAnimatorValue , crossinline callBack: () -> Unit
  ) {
  val animatorSet = AnimatorSet()
  val animatorArray = arrayListOf<Animator>()
  value.map {
    animatorArray.add(ObjectAnimator.ofFloat(this, it.animatorObject, it.finalValue))
    animatorSet.playTogether(animatorArray)
  }

  animatorSet.duration = AnimationDuration.Default
  animatorSet.interpolator = AccelerateDecelerateInterpolator()
  animatorSet.start()
  animatorSet.addListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator?) {}
    override fun onAnimationCancel(p0: Animator?) {}
    override fun onAnimationRepeat(p0: Animator?) {}
    override fun onAnimationEnd(p0: Animator?) {
      callBack()
    }
  })
}

/**
 * Support 5.0 and above
 */

fun View.addTouchRippleAnimation(backgroundColor: Int = Color.TRANSPARENT, rippleColor: Int, rippleMode: RippleMode, radius: Float = 0f) {

  val shape = GradientDrawable().apply {
    cornerRadius = radius
    shape = GradientDrawable.RECTANGLE
    setSize(matchParent, matchParent)
    setColor(backgroundColor)
  }
  val otherColorList = ColorStateList.valueOf(rippleColor)
  val colorList = ColorStateList.valueOf(rippleColor)
  isClickable = true
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    background = when (rippleMode) {
      RippleMode.Square -> RippleDrawable(colorList, shape, null)
      RippleMode.Round ->
        RippleDrawable(ColorStateList.valueOf(Color.TRANSPARENT), null, null)
          .apply {
            setColor(otherColorList)
          }
    }
  }
}