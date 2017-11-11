package com.blinnnk.honeyui

import android.util.Property
import android.view.View

/**
 * @date 11/11/2017 8:34 PM
 * @author KaySaith
 */

data class ValueAnimationOfIntParameters(
  val animationIntObject: AttributeAnimationType,
  val startValue: Int,
  val endValue: Int
)

data class ObjectAnimatorValue(
  val animatorObject: Property<View, Float>,
  val finalValue: Float
)