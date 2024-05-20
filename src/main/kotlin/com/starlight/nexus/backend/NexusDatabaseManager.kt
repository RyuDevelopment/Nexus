package com.starlight.nexus.backend

import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

object NexusDatabaseManager {

    lateinit var client: com.mongodb.MongoClient
    lateinit var database: MongoDatabase
    lateinit var profiles: MongoCollection<Document>

    @JvmStatic
    fun onEnable() {
        try {
            //val connectionString = "mongodb://admin:seNw&D{h9{e[gn}ps.{pZ)mVT7,i63sV@localhost:27017/?authSource=admin"
            //val settings = MongoClientSettings.builder().applyConnectionString(ConnectionString(connectionString)).build()

            this.client = com.mongodb.MongoClient(MongoClientURI("mongodb://localhost:27017"))
            //this.client = MongoClients.create(settings)

            println("[Mongo] Successfully connected to database!")
        } catch (e: Exception) {
            println("[Mongo] Failed to connect to database!")
            e.printStackTrace()
        }

        database = this.client.getDatabase("nexus")

        profiles = database.getCollection("profiles")
    }

    @JvmStatic
    fun onDisable() {
        client.close()
    }

}