package com.starlight.nexus.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

class JsonChain {

    var json = JsonObject()

    fun addProperty(property: String, value: String): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun addProperty(property: String, value: Number): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun addProperty(property: String, value: Boolean): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun addProperty(property: String, value: Char): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun add(property: String, element: JsonElement): JsonChain {
        json.add(property, element)
        return this
    }

    fun get(): JsonObject {
        return json
    }
}
