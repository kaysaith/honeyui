package com.blinnnk.extension

import android.support.v4.app.Fragment


/**
 * Many cases of the project will appear with `parentFragment` converted to the
 * specified` Fragment` scene Encapsulate this method to minimize the number of
 * transitions in the code, improve code cleanliness and security.
 */
inline fun <reified T : Fragment> Fragment.findFragmentByTag(fragmentTag: String) =
  fragmentManager?.findFragmentByTag(fragmentTag) as? T

inline fun <reified T : Fragment> Fragment.findChildFragmentByTag(fragmentTag: String) =
  childFragmentManager?.findFragmentByTag(fragmentTag) as? T

inline fun <reified T : Fragment> Fragment.getParentFragment(block: T.() -> Unit) {
  if (parentFragment is T) {
    block((parentFragment as T))
  } else return
}