package com.blinnnk.extension

import android.os.Handler
import java.math.BigInteger
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun <T : Number> T.isEven(): Boolean = this.toInt() % 2 == 0

infix fun Long.timeUpThen(doThis: () -> Unit) {
  Handler().postDelayed({
    doThis()
  }, this)
}

fun BigInteger?.orZero() = if (isNull()) BigInteger.ZERO else this!!


@UseExperimental(ExperimentalContracts::class)
inline fun Double?.hasValue(): Boolean {
  contract {
    returns(true) implies (this@hasValue != null)
  }
  return this != null && this > 0.0
}

@UseExperimental(ExperimentalContracts::class)
inline fun Int?.hasValue(): Boolean {
  contract {
    returns(true) implies (this@hasValue != null)
  }
  return this != null && this > 0
}

@UseExperimental(ExperimentalContracts::class)
inline fun Long?.hasValue(): Boolean {
  contract {
    returns(true) implies (this@hasValue != null)
  }
  return this != null && this > 0
}

@UseExperimental(ExperimentalContracts::class)
inline fun BigInteger?.hasValue(): Boolean {
  contract {
    returns(true) implies (this@hasValue != null)
  }
  return this != null && this > BigInteger.ZERO
}