@file:Suppress("DEPRECATED_IDENTITY_EQUALS", "DEPRECATION")

package com.blinnnk.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager


/**
 * @author KaySaith
 * @date  2018/11/13
 */
object Connectivity {

  fun getNetworkInfo(context: Context): NetworkInfo? {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo
  }

  fun isConnected(context: Context): Boolean {
    val info = Connectivity.getNetworkInfo(context)
    return info != null && info.isConnected
  }

  fun isConnectedWifi(context: Context): Boolean {
    val info = Connectivity.getNetworkInfo(context)
    return !(info == null || !info.isConnected || info.type !== ConnectivityManager.TYPE_WIFI)
  }


  fun isConnectedMobile(context: Context): Boolean {
    val info = Connectivity.getNetworkInfo(context)
    return info != null && info.isConnected && info.type === ConnectivityManager.TYPE_MOBILE
  }

  fun isConnectedFast(context: Context): Boolean {
    val info = Connectivity.getNetworkInfo(context)
    return info != null && info.isConnected && Connectivity.isConnectionFast(info.type, info.subtype)
  }

  fun isConnectionFast(type: Int, subType: Int): Boolean {
    return when (type) {
      ConnectivityManager.TYPE_WIFI -> true
      ConnectivityManager.TYPE_MOBILE -> when (subType) {
        TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
        TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
        TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
        TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
        TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
        TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
        TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
        TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
        TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
        TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
        /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
        TelephonyManager.NETWORK_TYPE_EHRPD // API level 11
        -> true // ~ 1-2 Mbps
        TelephonyManager.NETWORK_TYPE_EVDO_B // API level 9
        -> true // ~ 5 Mbps
        TelephonyManager.NETWORK_TYPE_HSPAP // API level 13
        -> true // ~ 10-20 Mbps
        TelephonyManager.NETWORK_TYPE_IDEN // API level 8
        -> false // ~25 kbps
        TelephonyManager.NETWORK_TYPE_LTE // API level 11
        -> true // ~ 10+ Mbps
        // Unknown
        TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
        else -> false
      }
      else -> false
    }
  }
}