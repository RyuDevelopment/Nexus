package com.starlight.nexus.util

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object ActionBarUtil {
    private val PENDING_MESSAGES: MutableMap<Player, BukkitTask> = HashMap()

    /**
     * Sends a message to the player's action bar.
     *
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param message the message to send.
     */
    fun sendActionBarMessage(bukkitPlayer: Player, message: String) {
        sendRawActionBarMessage(bukkitPlayer, "{\"text\": \"$message\"}")
    }

    /**
     * Sends a raw message (JSON format) to the player's action bar. Note: while the action bar accepts raw messages
     * it is currently only capable of displaying text.
     *
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param rawMessage the json format message to send.
     */
    fun sendRawActionBarMessage(bukkitPlayer: Player, rawMessage: String) {
        val player = bukkitPlayer as CraftPlayer
        val chatBaseComponent: IChatBaseComponent = ChatSerializer.a(rawMessage)
        val packetPlayOutChat = PacketPlayOutChat(chatBaseComponent, 2.toByte())
        player.handle.playerConnection.sendPacket(packetPlayOutChat)
    }

    /**
     * Sends a message to the player's action bar that lasts for an extended duration.
     *
     * The message will appear above the player's hot bar for the specified duration and fade away during the last
     * second of the duration.
     *
     * Only one long duration message can be sent at a time per player. If a new message is sent via this message
     * any previous messages still being displayed will be replaced.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param message the message to send.
     * @param duration the duration the message should be visible for in seconds.
     * @param plugin the plugin sending the message.
     */
    fun sendActionBarMessage(bukkitPlayer: Player, message: String, duration: Int, plugin: Plugin) {
        cancelPendingMessages(bukkitPlayer)
        val messageTask = object : BukkitRunnable() {
            private var count = 0

            override fun run() {
                if (count >= duration - 3) {
                    cancel()
                }
                sendActionBarMessage(bukkitPlayer, message)
                count++
            }
        }.runTaskTimer(plugin, 0L, 20L)
        PENDING_MESSAGES[bukkitPlayer] = messageTask
    }

    private fun cancelPendingMessages(bukkitPlayer: Player) {
        if (PENDING_MESSAGES.containsKey(bukkitPlayer)) {
            PENDING_MESSAGES[bukkitPlayer]?.cancel()
        }
    }
}