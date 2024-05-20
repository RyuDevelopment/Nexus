package com.starlight.nexus.util.time

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

class Duration(private var time: Long) {

    fun get(): Long {
        return time
    }

    fun isPermanent(): Boolean {
        return time == Long.MAX_VALUE
    }

    companion object {
        @JvmStatic
        val NOT_PROVIDED = Duration(-1L)

        @JvmStatic
        fun parse(input: String): Duration {
            if (input == "NOT_PROVIDED") {
                return NOT_PROVIDED
            }

            if (input.equals("perm", ignoreCase = true) || input.equals("permanent", ignoreCase = true)) {
                return Duration(0L)
            }

            return Duration(
                DateUtil.parseDuration(
                    input
                )
            )
        }
    }

    class DurationParameterType : ParameterType<Duration> {
        override fun transform(sender: CommandSender, source: String): Duration? {
            return try {
                parse(input = source)
            } catch (e: Exception) {
                sender.sendMessage("${ChatColor.RED}Invalid duration.")
                null
            }
        }

        override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
            return emptyList()
        }
    }

}