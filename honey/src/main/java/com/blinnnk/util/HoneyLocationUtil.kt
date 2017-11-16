package com.blinnnk.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.blinnnk.extension.safeToast
import com.blinnnk.honey.R
import org.json.JSONArray
import org.json.JSONObject

/**
 * @date 16/11/2017 1:23 PM
 * @author KaySaith
 * @description
 */

/** Returns the LocationManager instance. **/
val Context.honeyLocationManager: LocationManager
  get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

data class LocationInfo(val longitude: Double, val latitude: Double)

data class AddressDetail(val city: String?, val area: String?, val locationInfo: LocationInfo)

fun Context.getLocationManager(): LocationManager {
  return getSystemService(Context.LOCATION_SERVICE) as LocationManager
}

@SuppressLint("MissingPermission")
fun Activity.getLocationListener(block: AddressDetail.() -> Unit) {
  honeyLocationManager.getProviders(true).apply {
    val provider = when {
      contains(LocationManager.GPS_PROVIDER) ->
        LocationManager.GPS_PROVIDER
      contains(LocationManager.NETWORK_PROVIDER) ->
        LocationManager.NETWORK_PROVIDER
      contains(LocationManager.PASSIVE_PROVIDER) ->
        LocationManager.PASSIVE_PROVIDER

      else -> {
        safeToast("请检查网络或GPS是否打开")
        return
      }
    }
    val location = honeyLocationManager.getLastKnownLocation(provider)
    if (location != null) {
      block(calculateExactLocation(LocationInfo(location.longitude, location.latitude)))
    } else {
      getLocationManager().requestLocationUpdates(provider, 5000, 10f,
        object : LocationListener {
          override fun onLocationChanged(location: Location) {
            // 计算具体的地理位置
            block(calculateExactLocation(LocationInfo(location.longitude, location.latitude)))
          }

          override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
          override fun onProviderEnabled(provider: String) {}
          override fun onProviderDisabled(provider: String) {}
        }
      )
    }
  }
}

/**
 * @description
 * 把本地的 `Json` 地址按照省会为大维度转换成 [PlaceModel] 格式
 */
fun Context.formatLocation(): ArrayList<PlaceModel> {
  return arrayListOf<PlaceModel>().apply {
    JSONArray(convertLocalJsonFileToJSONObjectArray(R.raw.honey_china_location))
      .let { placeData ->
        (0 until placeData.length())
          .mapTo(this) {
            PlaceModel(placeData.getJSONObject(it))
          }
      }
  }
}

/**
 * @description
 * 先计算与自己经度最近的省会或直辖市, 再进入对应的二级城市或直辖市区进行检索与自己最近的.
 * 最后返回 [AddressDetail]. 因为目前产品只在中国, 没有出北半球, 纬度暂时没有使用.
 */
fun Context.calculateExactLocation(locationInfo: LocationInfo): AddressDetail {
  var exactLocation: AddressDetail? = null
  formatLocation()
    .minBy { Math.abs(it.longitude!! - locationInfo.longitude) }
    ?.let {
      it.getChildList()
        .minBy { child -> Math.abs(child.longitude!! - locationInfo.longitude) }
        ?.apply {
          exactLocation = AddressDetail(it.name, this.name,
            LocationInfo(it.latitude!!, it.longitude!!)
          )
        }
    }
  return exactLocation!!
}

/**
 * @description
 * 地理位置的 `model`, 在准备数据的时候方便调用. `model` 里面带了两个方法分别是用来
 * 准备二级城市的 `JsonArray` 以及把二级城市转换成 `arrayList<PlaceModel>` 的.
 * [childArea] 是在二级城市计算的时候临时存储已经计算出来的列表，提升性能用的. 不给外界代用.
 */
class PlaceModel(val data: JSONObject?) {

  val name: String? = data?.getString("name")
  val longitude: Double? = data?.getString("log")?.toDouble()
  val latitude: Double? = data?.getString("lat")?.toDouble()

  private lateinit var childArea: JSONArray

  private fun getSubArea(): JSONArray {
    return JSONArray(data?.getString("children"))
  }

  fun getChildList(): ArrayList<PlaceModel> {
    childArea = getSubArea()
    return arrayListOf<PlaceModel>().apply {
      (0 until childArea.length()).map { add(PlaceModel(childArea.getJSONObject(it))) }
    }
  }

}