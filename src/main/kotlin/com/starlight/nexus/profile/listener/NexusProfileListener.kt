package com.starlight.nexus.profile.listener

import com.starlight.nexus.profile.NexusProfile
import com.starlight.nexus.profile.NexusProfileManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 29/2/2024 - 23:01
*/

class NexusProfileListener : Listener {

    @EventHandler
    fun onAsyncPreLoginEvent(event: AsyncPlayerPreLoginEvent) {
        var profile: NexusProfile? = NexusProfileManager.getProfile(event.uniqueId)

        if (profile == null) {
            profile = NexusProfile(event.uniqueId, event.name)
        } else {
            profile.load()
        }

        profile.save()
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        NexusProfileManager.profiles.remove(event.player.uniqueId)
    }

}