package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PlayerParameterType : ParameterType<Player?> {

    override fun transform(sender: CommandSender, source: String): Player? {
        if (sender is Player && (source.equals("self", true) || source.isEmpty())) {
            return sender
        }

        val player = Bukkit.getServer().getPlayer(source)

        if (player == null) {
            sender.sendMessage("${ChatColor.RED}No player with the name $source found.")
            return null
        }

        return player
    }

    // note: player argument variable is the sender that is requesting tab completion
    override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
        val completions = ArrayList<String>()

        for (target in Bukkit.getOnlinePlayers()) {
            // if the sender cannot see the target, don't allow them to see ENSURE THAT HAS META
            if (!player.canSee(target) && (target.hasMetadata("invisible") || target.hasMetadata("modmode"))) continue
            completions.add(target.name)
        }

        return completions
    }

}