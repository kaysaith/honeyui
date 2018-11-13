@file:Suppress("UNCHECKED_CAST", "EXTENSION_SHADOWED_BY_MEMBER")

package com.blinnnk.util

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

fun <T : Fragment> Fragment.getParentFragment() = parentFragment as? T

// 支持传递 `bundle` 参数
inline fun <reified T : Fragment> Fragment.replaceFragmentAndSetArgument(
	containerID: Int,
	setArgument: Bundle.() -> Unit = {}
) {
	val willAppearFragment = T::class.java.newInstance()
	val bundle = Bundle()
	setArgument(bundle)
	willAppearFragment.arguments = bundle
	childFragmentManager
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.replace(containerID, willAppearFragment)
		.commitAllowingStateLoss()
}

inline fun <reified T : Fragment> Fragment.addFragmentAndSetArgument(
	containerID: Int,
	setArgument: Bundle.() -> Unit = {}
) {
	val willAppearFragment = T::class.java.newInstance()
	val bundle = Bundle()
	setArgument(bundle)
	willAppearFragment.arguments = bundle
	childFragmentManager
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.add(containerID, willAppearFragment)
		.commitAllowingStateLoss()
}

inline fun <reified T : Fragment> Fragment.addFragmentAndSetArgument(
	containerID: Int,
	fragmentTag: String = "",
	setArgument: Bundle.() -> Unit = {}
) {
	val willAppearFragment = T::class.java.newInstance()
	val bundle = Bundle()
	setArgument(bundle)
	willAppearFragment.arguments = bundle
	childFragmentManager
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.add(containerID, willAppearFragment, fragmentTag)
		.commitAllowingStateLoss()
}