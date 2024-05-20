package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FloatParameterType : ParameterType<Float?> {

    override fun transform(sender: CommandSender, source: String): Float? {
        try {
            val parsed = java.lang.Float.parseFloat(source.replace(",", "").replace("_", ""))

            if (java.lang.Float.isNaN(parsed) || !java.lang.Float.isFinite(parsed)) {
                sender.sendMessage("${ChatColor.RED}$source is not a valid number.")
                return null
            }

            return parsed
        } catch (exception: NumberFormatException) {
            sender.sendMessage("${ChatColor.RED}$source is not a valid number.")
            return null
        }
    }

    override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
        return listOf()
    }

}