package com.blinnnk.extension

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.ViewGroup
import com.blinnnk.animation.fallOut

/**
 * Many cases of the project will appear with `parentFragment` converted to the
 * specified` Fragment` scene Encapsulate this method to minimize the number of
 * transitions in the code, improve code cleanliness and security.
 */
inline fun <reified T : Fragment> Fragment.findFragmentByTag(fragmentTag: String) =
  fragmentManager?.findFragmentByTag(fragmentTag) as? T

inline fun <reified T : Fragment> Fragment.findChildFragmentByTag(fragmentTag: String) =
  childFragmentManager.findFragmentByTag(fragmentTag) as? T

inline fun <reified T : Fragment> Fragment.getParentFragment(block: T.() -> Unit) {
  if (parentFragment is T) {
    block((parentFragment as T))
  } else return
}

inline fun <reified T : Fragment> Fragment.addFragmentAndSetArgument(containerID: Int, vararg arguments: Pair<String, Any>): T {
  val willAppearFragment = T::class.java.newInstance()
  willAppearFragment.arguments = Bundle().apply {
    arguments.forEach {
      val value = it.second
      when (value) {
        is Int -> putInt(it.first, value)
        is Double -> putDouble(it.first, value)
        is String -> putString(it.first, value)
        is Float -> putFloat(it.first, value)
        is Boolean -> putBoolean(it.first, value)
      }
    }
  }
  childFragmentManager
    .beginTransaction()
    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
    .add(containerID, willAppearFragment)
    .commitAllowingStateLoss()
  return willAppearFragment
}

/**
 * @description A convenient operation of  `Fragment`
 */

@JvmOverloads
inline fun <T : Fragment> T.removeChildFragment(fragment: Fragment, callback: () -> Unit = {}) {
  childFragmentManager.beginTransaction().remove(fragment).commit()
  callback()
}

fun <T : Fragment> T.hideChildFragment(fragment: Fragment) {
  childFragmentManager.beginTransaction().hide(fragment).commit()
}

fun <T : Fragment> T.showChildFragment(fragment: Fragment) {
  childFragmentManager.beginTransaction().show(fragment).commit()
}

/**
 * @description
 * 这个函数是用来安全移除黑蜜的主题 `ContentOverlay` 的。
 * 因为黑蜜的设计大多数的内容场景都是在悬浮层上实现的。所以有自己特殊的构造结构
 * 为此从 `Activity` 写在 `Fragment` 的时候要先要实现对应 `View` 上的动画.
 * 为此设置了这个便捷的函数.
 * @param [animationView] 需要做动画移除的 `subView`
 */

fun Fragment.removeSelfWithAnimation(animationView: ViewGroup?, callback: () -> Unit = { }) {
  activity?.let {
    animationView?.fallOut {
      it.supportFragmentManager.beginTransaction().remove(this).commit()
      callback()
    }
  }
}

inline fun <reified T : Fragment> Fragment.getChildFragment(): T? {
  return try {
    childFragmentManager.fragments.find { it is T } as? T
  } catch (error: Exception) {
    Log.e("getChildFragment", "$error")
    null
  }
}

inline fun <reified T> Fragment.getGrandFather(): T? {
  return when {
    parentFragment?.parentFragment.isNull() -> null
    parentFragment?.parentFragment!! is T -> parentFragment?.parentFragment!! as T
    else -> null
  }
}