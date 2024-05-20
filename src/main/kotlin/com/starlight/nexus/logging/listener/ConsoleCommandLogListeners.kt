package com.starlight.nexus.logging.listener

import com.starlight.nexus.Nexus
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerCommandEvent

object ConsoleCommandLogListeners : Listener {

    @EventHandler
    fun onServerCommandEvent(event: ServerCommandEvent) {
        if (event.sender is ConsoleCommandSender) {
            Nexus.instance.logger.info("[Console Command] ${event.command}")
        }
    }

}
