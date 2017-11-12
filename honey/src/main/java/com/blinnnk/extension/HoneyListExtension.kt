package com.blinnnk.honey

import java.util.*

fun <T> List<T>.getRandom(): T = this[Random().nextInt(this.count())]

fun <T> List<T>.randomIndex(): Int = Random().nextInt(this.count())
