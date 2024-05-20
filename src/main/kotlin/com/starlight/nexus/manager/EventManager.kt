package com.starlight.nexus.manager

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

/*
    * Author: T4yrn
    * Project: nexus
    * Date: 14/2/2024 - 16:04
*/

class EventManager(
    private val plugin: JavaPlugin
) {

    private val pluginManager: PluginManager = plugin.server.pluginManager

    companion object {
        private lateinit var instance: EventManager

        fun initialize(javaPlugin: JavaPlugin) {
            if (!Companion::instance.isInitialized) {
                instance = EventManager(javaPlugin)
            }
        }

        fun <T : Event> subscribe(
            eventClass: KClass<T>,
            priority: EventPriority = EventPriority.NORMAL,
            handler: T.() -> Unit
        ) {
            val listener = object : Listener {}
            val eventHandler: (Listener, Event) -> Unit = { _, event ->
                @Suppress("UNCHECKED_CAST")
                (event as? T)?.handler()
            }

            instance.pluginManager.registerEvent(eventClass.java, listener, priority, eventHandler, instance.plugin)
        }
    }

}