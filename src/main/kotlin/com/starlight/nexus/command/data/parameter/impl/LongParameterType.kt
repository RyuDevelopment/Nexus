package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LongParameterType : ParameterType<Long?> {

    override fun transform(sender: CommandSender, value: String): Long? {
        return try {
            value.replace(",", "").replace("_", "").toLong()
        } catch (exception: NumberFormatException) {
            sender.sendMessage("${ChatColor.RED}$value is not a valid number.")
            null
        }
    }

    override fun tabComplete(sender: Player, flags: Set<String>, prefix: String): List<String> {
        return listOf()
    }

}