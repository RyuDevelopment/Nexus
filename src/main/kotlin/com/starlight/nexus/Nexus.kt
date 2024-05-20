package com.starlight.nexus

import com.starlight.nexus.backend.NexusDatabaseManager
import com.starlight.nexus.command.CommandHandler
import com.starlight.nexus.manager.ClassManager
import com.starlight.nexus.manager.EventManager
import com.starlight.nexus.profile.listener.NexusProfileListener
import org.bukkit.plugin.java.JavaPlugin

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 29/2/2024 - 17:22
*/

object Nexus {

    lateinit var instance: JavaPlugin

    var viaVersionFind = false
    var protocolLibFind = false
    var lunarApiFind = false

    @JvmStatic
    fun onEnable() {
        if (instance.server.pluginManager.getPlugin("ViaVersion") != null) {
            viaVersionFind = true
        }
        if (instance.server.pluginManager.getPlugin("ProtocolLib") != null) {
            protocolLibFind = true
        }
        if (instance.server.pluginManager.getPlugin("Apollo-Bukkit") != null) {
            lunarApiFind = true
        }

        NexusDatabaseManager.onEnable();
        instance.server.pluginManager.registerEvents(NexusProfileListener(), instance);

        EventManager.initialize(instance)
        ClassManager.initialize(instance)

        setupCommandAPI()
    }

    @JvmStatic
    fun onDisable() {
        CommandHandler.onDisable()
        NexusDatabaseManager.onDisable()
    }

    private fun setupCommandAPI() {
        CommandHandler.onEnable()
    }

}