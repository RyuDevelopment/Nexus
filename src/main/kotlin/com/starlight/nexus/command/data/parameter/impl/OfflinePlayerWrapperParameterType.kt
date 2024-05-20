package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import com.starlight.nexus.util.player.OfflinePlayerWrapper
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OfflinePlayerWrapperParameterType : ParameterType<OfflinePlayerWrapper> {

    override fun transform(sender: CommandSender, source: String): OfflinePlayerWrapper {
        return OfflinePlayerWrapper(source)
    }

    override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
        val completions = ArrayList<String>()
        for (target in Bukkit.getOnlinePlayers()) {
            if (!player.canSee(target) && (target.hasMetadata("invisible") || target.hasMetadata("modmode"))) continue

            completions.add(target.name)
        }
        return completions
    }

}