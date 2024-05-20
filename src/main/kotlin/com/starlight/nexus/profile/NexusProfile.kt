package com.starlight.nexus.profile

import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import com.starlight.nexus.backend.NexusDatabaseManager
import org.bson.Document
import org.bukkit.Bukkit
import java.util.*

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 29/2/2024 - 23:00
*/

class NexusProfile {

    var uuid: UUID
    var name: String
    var performanceMode: Boolean = false
    var uuidCache: Boolean = false

    constructor(uuid: UUID) {
        this.uuid = uuid
        this.name = Bukkit.getOfflinePlayer(uuid).name

        load()
    }

    constructor(name: String) {
        this.uuid = Bukkit.getOfflinePlayer(name).uniqueId
        this.name = name

        load()
    }

    constructor(uuid: UUID, name: String) {
        this.uuid = uuid
        this.name = name

        load()
    }

    fun load() {
        val document = NexusDatabaseManager.profiles.find(Filters.eq("uuid", this.uuid.toString())).first()

        if(uuid == null || document == null) {
            return;
        }

        this.uuid = UUID.fromString(document.getString("uuid"))
        this.name = document.getString("name")
        this.performanceMode = if (document.contains("performanceMode")) document.getBoolean("performanceMode") else false
        this.uuidCache = if (document.contains("uuidCache")) document.getBoolean("uuidCache") else false
    }

    fun save() {
        val document = Document()

        document["uuid"] = this.uuid.toString()
        document["name"] = this.name
        document["performanceMode"] = this.performanceMode
        document["uuidCache"] = this.uuidCache

        NexusDatabaseManager.profiles.replaceOne(Filters.eq("uuid", this.uuid.toString()), document, ReplaceOptions().upsert(true))
    }

}