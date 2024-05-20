package com.starlight.nexus.util

import com.google.gson.JsonParser
import com.starlight.nexus.logging.ErrorHandler
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

abstract class UUIDCache {

    companion object {
        private val client = OkHttpClient()
        private val parser = JsonParser()

        /**
         * Fetches a UUID and Username pair from the Mojang API.
         * Should not be called very often as the server could be rate-limited which will cause more issues.
         */
        @JvmStatic
        fun fetchFromMojang(username: String): Optional<Pair<UUID, String>> {
            try {
                val request = Request.Builder().url("https://api.mojang.com/users/profiles/minecraft/$username").build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val json = parser.parse(response.body().string()).asJsonObject

                    val uuid = UUID.fromString(json.get("id").asString.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(), "$1-$2-$3-$4-$5"))
                    val name = json.get("name").asString

                    return Optional.of(Pair(uuid, name))
                }
            } catch (e: Exception) {
                ErrorHandler.generateErrorLog(
                    errorType = "mojang-api-fetch",
                    event = mapOf("username" to username),
                    exception = e
                )
            }

            return Optional.ofNullable(null)
        }
    }

}