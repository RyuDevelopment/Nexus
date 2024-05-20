package com.starlight.nexus.util.player

import com.mojang.authlib.GameProfile
import com.starlight.nexus.Nexus
import com.starlight.nexus.util.Tasks
import net.minecraft.server.v1_8_R3.EntityPlayer
import net.minecraft.server.v1_8_R3.PlayerInteractManager
import net.minecraft.server.v1_8_R3.World
import org.bukkit.craftbukkit.v1_8_R3.CraftServer
import org.bukkit.entity.Player
import java.util.*

class OfflinePlayerWrapper(var source: String) {

    var uniqueId: UUID? = null
    var name: String? = null

    fun loadAsync(callback: (Player?) -> Unit) {
        Tasks.async {
            val player = loadSync()

            Tasks.sync {
                callback.invoke(player)
            }
        }
    }

    fun loadSync(): Player? {
        if (!(source[0] != '\"' && source[0] != '\'' || source[source.length - 1] != '\"' && source[source.length - 1] != '\'')) {
            source = source.replace("'", "").replace("\"", "")
            uniqueId = Nexus.instance.server.getOfflinePlayer(source).uniqueId
            if (uniqueId == null) {
                name = source
                return null
            }
            name = Nexus.instance.server.getOfflinePlayer(uniqueId).name
            if (Nexus.instance.server.getPlayer(uniqueId) != null) {
                return Nexus.instance.server.getPlayer(uniqueId)
            }
            if (!Nexus.instance.server.getOfflinePlayer(uniqueId).hasPlayedBefore()) {
                return null
            }
            val server = (Nexus.instance.server as CraftServer).server
            val entity = EntityPlayer(
                server,
                server.getWorldServer(0),
                GameProfile(uniqueId, name),
                PlayerInteractManager(server.getWorldServer(0) as World)
            )
            val player = entity.bukkitEntity
            player?.loadData()
            return player
        }
        if (Nexus.instance.server.getPlayer(source) != null) {
            return Nexus.instance.server.getPlayer(source)
        }
        uniqueId = Nexus.instance.server.getOfflinePlayer(source).uniqueId
        if (uniqueId == null) {
            name = source
            return null
        }
        name = Nexus.instance.server.getOfflinePlayer(uniqueId).name
        if (Nexus.instance.server.getPlayer(uniqueId) != null) {
            return Nexus.instance.server.getPlayer(uniqueId)
        }
        if (!Nexus.instance.server.getOfflinePlayer(uniqueId).hasPlayedBefore()) {
            return null
        }
        val server = (Nexus.instance.server as CraftServer).server
        val entity = EntityPlayer(
            server,
            server.getWorldServer(0),
            GameProfile(uniqueId, name),
            PlayerInteractManager(server.getWorldServer(0) as World)
        )
        val player = entity.bukkitEntity
        player?.loadData()
        return player
    }

}