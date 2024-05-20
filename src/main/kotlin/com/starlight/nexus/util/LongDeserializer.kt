package com.starlight.nexus.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

object LongDeserializer : JsonDeserializer<Long> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Long {
        return if (json.isJsonObject && json.asJsonObject.has("\$numberLong")) {
            json.asJsonObject.get("\$numberLong").asLong
        } else {
            json.asLong
        }
    }

}