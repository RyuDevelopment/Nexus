package com.starlight.nexus.profile

import com.starlight.nexus.backend.NexusDatabaseManager
import com.starlight.nexus.data.IManager
import org.bson.Document
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 29/2/2024 - 23:00
*/

object NexusProfileManager : IManager {

    val profiles: ConcurrentMap<UUID, NexusProfile> = ConcurrentHashMap()

    override fun onEnable() {
        for (document in NexusDatabaseManager.profiles.find()) {
            val nexusProfile = parse(document)
            profiles[nexusProfile.uuid] = nexusProfile
        }
        println("[Nexus] Loading " + profiles.size + " profile" + (if (profiles.size > 1) "s" else "") + " in the database")
    }

    override fun onDisable() {

    }

    fun getProfile(id: UUID):NexusProfile? {
        return this.profiles[id]
    }

    fun getProfile(name: String): NexusProfile? {
        return name.let { profile ->
            profiles.values.firstOrNull { it.name == profile }
        }
    }

    private fun parse(document: Document): NexusProfile {
        return NexusProfile(UUID.fromString(document.getString("uuid")))
    }

}