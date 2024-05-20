package com.starlight.nexus.util

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object ParticleMath {

    private const val SIN_BITS = 12
    private const val SIN_MASK = (-1 shl SIN_BITS).inv()
    private const val SIN_COUNT = SIN_MASK + 1

    private const val DEG_FULL = 360.0
    private const val RAD_FULL = Math.PI * 2.0

    private const val RAD_TO_INDEX = SIN_COUNT / this.RAD_FULL
    private const val DEG_TO_INDEX = SIN_COUNT / this.DEG_FULL

    private val sin = arrayOfNulls<Double>(SIN_COUNT)
    private val cos = arrayOfNulls<Double>(SIN_COUNT)

    init {
        for (i in 0 until SIN_COUNT) {
            this.sin[i] = kotlin.math.sin((i + 0.5F) / SIN_COUNT * RAD_FULL)
            this.cos[i] = kotlin.math.cos((i + 0.5F) / SIN_COUNT * RAD_FULL)
        }

        for (i in 0 until 360 step 90) {
            this.sin[(i * DEG_TO_INDEX).toInt() and SIN_MASK] = kotlin.math.sin(i * Math.PI / 180.0)
            this.cos[(i * DEG_TO_INDEX).toInt() and SIN_MASK] = kotlin.math.cos(i * Math.PI / 180.0)
        }

    }

    fun sin(rad: Double): Double {
        return this.sin[(rad * RAD_TO_INDEX).toInt() and SIN_MASK]!!
    }

    fun cos(rad: Double): Double {
        return this.cos[(rad * RAD_TO_INDEX).toInt() and SIN_MASK]!!
    }

}