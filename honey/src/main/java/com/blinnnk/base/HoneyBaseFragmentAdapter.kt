package com.blinnnk.base

import android.annotation.SuppressLint
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.ViewGroup
import com.blinnnk.extension.isNull

@Suppress("DEPRECATION")
/**
 * @date 27/07/2017.
 * @author KaySaith
 */

data class SubFragment(val fragment: Fragment, val tag: String)

class HoneyBaseFragmentAdapter(
  private val fragmentManager: FragmentManager?,
  private var fragmentList: ArrayList<SubFragment>
) : FragmentStatePagerAdapter(fragmentManager) {

  private var mCurTransaction: FragmentTransaction? = null
  private var mCurrentPrimaryItem: Fragment? = null

  override fun startUpdate(container: ViewGroup) {}

  override fun getCount(): Int = fragmentList.size

  override fun isViewFromObject(view: View, `object`: Any): Boolean = (`object` as Fragment).view === view

  override fun getItem(position: Int): Fragment? = fragmentList[position].fragment

  @SuppressLint("CommitTransaction")
  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    if(mCurTransaction.isNull()) mCurTransaction = fragmentManager?.beginTransaction()
    // 将Fragment移出UI,但并未从FragmentManager中移除
    mCurTransaction?.detach(`object` as Fragment)
  }

  override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
    val fragment = `object` as Fragment
    if (fragment != mCurrentPrimaryItem) {
      // 主要项切换,相关菜单及信息进行切换
      if (mCurrentPrimaryItem != null) {
        mCurrentPrimaryItem?.setMenuVisibility(false)
        mCurrentPrimaryItem?.userVisibleHint = false
      }
      fragment.setMenuVisibility(true)
      fragment.userVisibleHint = true
      mCurrentPrimaryItem = fragment
    }
  }

  @SuppressLint("CommitTransaction")
  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    super.instantiateItem(container, position)
    if (mCurTransaction.isNull()) mCurTransaction = fragmentManager?.beginTransaction()
    var subFragment = fragmentManager?.findFragmentByTag(fragmentList[position].tag)
    if (subFragment != null) {
      mCurTransaction?.attach(subFragment)
    } else {
      subFragment = fragmentList[position].fragment
      mCurTransaction?.add(container.id, subFragment, fragmentList[position].tag)
    }
    return subFragment
  }

  override fun finishUpdate(container: ViewGroup) {
    if (mCurTransaction != null) {
      // 提交事务
      mCurTransaction?.commitAllowingStateLoss()
      mCurTransaction = null
      // 立即运行等待中事务
      fragmentManager?.executePendingTransactions()
    }
  }

  override fun saveState(): Parcelable? = null

  override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

}