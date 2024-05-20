package com.starlight.nexus.util

import com.comphenix.protocol.ProtocolLibrary
import com.starlight.nexus.Nexus.protocolLibFind
import com.starlight.nexus.Nexus.viaVersionFind
import com.viaversion.viaversion.api.Via
import org.bukkit.entity.Player

val Player.protocol: Int
    get() = if (viaVersionFind) {
        Via.getAPI().getConnection(this.uniqueId)?.protocolInfo?.protocolVersion ?: 47
    } else if (protocolLibFind) {
        ProtocolLibrary.getProtocolManager().getProtocolVersion(this)
    } else {
        47
    }