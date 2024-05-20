package com.starlight.nexus.util

import org.bukkit.ChatColor
import java.text.DecimalFormat

object TPSUtil {

    fun formatTPS(tps: Double, shouldColor: Boolean): String {
        val format = DecimalFormat("##0.0")
        val colour: String = when {
            tps > 20.0 -> ChatColor.GREEN.toString()
            tps == 20.0 -> ChatColor.GREEN.toString() + "*"
            tps >= 15.0 -> ChatColor.YELLOW.toString()
            else -> ChatColor.RED.toString()
        }
        return if (shouldColor) colour + format.format(tps) else format.format(tps)
    }

}