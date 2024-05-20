package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OfflinePlayerParameterType : ParameterType<OfflinePlayer> {

    override fun transform(sender: CommandSender, source: String): OfflinePlayer {
        return if (sender is Player && (source.equals("self", ignoreCase = true) || source == "")) {
            sender
        } else Bukkit.getServer().getOfflinePlayer(source)
    }

    override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
        val completions = ArrayList<String>()

        for (target in Bukkit.getOnlinePlayers()) {
            if (!player.canSee(player)) {
                if (player.hasPermission("nexus.staff") && (target.hasMetadata("invisible")) || target.hasMetadata("modmode")) continue
                else !(target.hasMetadata("invisible") || target.hasMetadata("modmode"))
            } else continue

            completions.add(target.name)
        }

        return completions
    }

}