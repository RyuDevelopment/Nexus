package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GameModeParameterType : ParameterType<GameMode?> {

    override fun transform(sender: CommandSender, source: String): GameMode? {
        if (source != "-0*toggle*0-" || sender !is Player) {
            for (mode in GameMode.entries) {
                if (mode.name.equals(source, ignoreCase = true)) {
                    return mode
                }
                if (mode.value.toString().equals(source, ignoreCase = true)) {
                    return mode
                }
                if (StringUtils.startsWithIgnoreCase(mode.name, source)) {
                    return mode
                }
            }
            sender.sendMessage("${ChatColor.RED}No gamemode with the name " + source + " found.")
            return null
        }

        return if (sender.gameMode != GameMode.CREATIVE) {
            GameMode.CREATIVE
        } else {
            GameMode.SURVIVAL
        }
    }

    override fun tabComplete(sender: Player, flags: Set<String>, source: String): List<String> {
        val completions = ArrayList<String>()
        for (mode in GameMode.entries) {
            if (StringUtils.startsWithIgnoreCase(mode.name, source)) {
                completions.add(mode.name)
            }
        }
        return completions
    }

}