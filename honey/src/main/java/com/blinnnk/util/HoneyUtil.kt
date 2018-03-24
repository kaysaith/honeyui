package com.blinnnk.util

import android.view.View
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

// Same Like Swift `DidSet` and `WillSet`
fun <T> observing(
  initialValue: T,
  willSet: () -> Unit = { },
  didSet: () -> Unit = { }
) = object : ObservableProperty<T>(initialValue) {
  override fun beforeChange(
    property: KProperty<*>,
    oldValue: T,
    newValue: T
  ): Boolean = true.apply { willSet() }

  override fun afterChange(
    property: KProperty<*>,
    oldValue: T,
    newValue: T
  ) = didSet()
}


fun getHeight(view: View) = view.layoutParams.height