package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StringParameterType : ParameterType<String> {

    override fun transform(sender: CommandSender, source: String): String {
        return source
    }

    override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
        return listOf()
    }

}