package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class IntegerParameterType : ParameterType<Int?> {

    override fun transform(sender: CommandSender, value: String): Int? {
        return try {
            Integer.parseInt(value.replace(",", "").replace("_", ""))
        } catch (exception: NumberFormatException) {
            sender.sendMessage("${ChatColor.RED}$value is not a valid number.")
            null
        }
    }

    override fun tabComplete(sender: Player, flags: Set<String>, prefix: String): List<String> {
        return listOf()
    }

}