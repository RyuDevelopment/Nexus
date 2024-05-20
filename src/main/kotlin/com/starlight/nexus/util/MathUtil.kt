package com.starlight.nexus.util

import kotlin.math.abs

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object MathUtil {

    @JvmStatic
    fun isBetween(input: Int,min: Int,max: Int): Boolean {
        return input in min..max
    }

    @JvmStatic
    fun setPositive(input: Int): Int {
        return abs(input)
    }

    @JvmStatic
    fun floor(double: Double): Int {

        val int = double.toInt()

        return if (double < int.toDouble()) int - 1 else int
    }

}