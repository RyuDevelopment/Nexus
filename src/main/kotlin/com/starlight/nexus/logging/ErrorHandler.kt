package com.starlight.nexus.logging

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.starlight.nexus.Nexus
import com.starlight.nexus.util.Tasks
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

object ErrorHandler {

    private val CHAR_POOL: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val DATE_FORMAT = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")

    private val dataFolder: File = File(Nexus.instance.dataFolder.parentFile.parentFile, "errors")

    init {
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
    }

    private fun generateLogId(): String {
        return ThreadLocalRandom.current().ints(10, 0, CHAR_POOL.size).asSequence().map(CHAR_POOL::get).joinToString("")
    }

    @JvmStatic
    fun generateErrorLog(errorType: String, event: Map<String, String> = emptyMap(), exception: Exception): String {
        val date = Date.from(Instant.now())
        val logId = "$errorType-${generateLogId()}"
        val logFile = File(dataFolder, "$logId.txt")

        val stringBuilder = StringBuilder()
        stringBuilder.append("Generated at ${DATE_FORMAT.format(date)} by a ${exception.javaClass.name} exception thrown by a plugin event `$errorType`")
        stringBuilder.append("\n\n")
        stringBuilder.append("--- Event details ---")
        stringBuilder.append("\n")

        event.forEach { (field, value) ->
            stringBuilder.append("$field: $value")
            stringBuilder.append("\n")
        }

        stringBuilder.append("\n\n")
        stringBuilder.append("--- Execution details ---")
        stringBuilder.append("\n")
        stringBuilder.append("Message: ${exception.message}")
        stringBuilder.append("\n")
        stringBuilder.append("Type: ${exception.javaClass.name}")
        stringBuilder.append("\n\n")
        stringBuilder.append("--- Execution stack trace ---")
        stringBuilder.append("\n")

        exception.stackTrace.forEach { el ->
            stringBuilder.append(el.toString())
            stringBuilder.append("\n")
        }

        Tasks.async {
            Files.write(stringBuilder.toString(), logFile, Charsets.UTF_8)
        }

        Nexus.instance.logger.severe("Generated an error log at ${logFile.path} by an exception thrown by a plugin event `$errorType`")

        return logId
    }

}
