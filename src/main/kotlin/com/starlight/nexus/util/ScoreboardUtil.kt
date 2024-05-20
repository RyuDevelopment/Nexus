package com.starlight.nexus.util

import org.bukkit.ChatColor

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object ScoreboardUtil {

    @JvmStatic
    fun splitPrefixAndSuffix(text: String):Pair<String,String> {

        if (text.length <= 16) {
            return text to ""
        }

        var prefix = text.substring(0, 16)
        var suffix: String

        if (prefix[15] == ChatColor.COLOR_CHAR || prefix[15] == '&') {
            prefix = prefix.substring(0,15)
            suffix = text.substring(15,text.length)
        } else if (prefix[14] == ChatColor.COLOR_CHAR || prefix[14] == '&') {
            prefix = prefix.substring(0,14)
            suffix = text.substring(14,text.length)
        } else {
            suffix = "${ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&',prefix))}${text.substring(16,text.length)}"
        }

        if (suffix.length > 16) {
            suffix = suffix.substring(0,16)
        }

        return prefix to suffix
    }

}