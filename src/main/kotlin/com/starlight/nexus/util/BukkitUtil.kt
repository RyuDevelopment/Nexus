package com.starlight.nexus.util

import java.util.*

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object BukkitUtil {

    fun isUUID(str: String): Boolean {
        return try {
            UUID.fromString(str)
            true
        } catch (e: Exception) {
            false
        }
    }

}