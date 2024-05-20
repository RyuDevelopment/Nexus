package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import com.starlight.nexus.profile.NexusProfile
import com.starlight.nexus.profile.NexusProfileManager
import com.starlight.nexus.util.UUIDCache
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class UUIDParameterType : ParameterType<UUID?> {

    override fun transform(sender: CommandSender, source: String): UUID? {
        if (sender is Player && (source.equals("self", ignoreCase = true) || source == "")) {
            return sender.uniqueId
        }

        val uuid: UUID

        try {
            uuid = UUID.fromString(source)
            return uuid
        } catch (e: Exception) {
            val optionalProfile = UUIDCache.fetchFromMojang(source)
            if (optionalProfile.isPresent) {
                return optionalProfile.get().first
            } else {
                val nexusProfile: NexusProfile
                try {
                    nexusProfile = NexusProfileManager.getProfile(source)!!
                    nexusProfile.uuid
                } catch (e: Exception) {
                    sender.sendMessage("${ChatColor.RED}Your input did not match a player username or player UUID.")
                    return null
                }
            }
        }
        return null
    }

    override fun tabComplete(sender: Player, flags: Set<String>, source: String): List<String> {
        val completions = ArrayList<String>()

        for (player in Bukkit.getOnlinePlayers()) {
            if (!sender.canSee(player)) {
                continue
            }

            completions.add(player.name)
        }

        return completions
    }
}