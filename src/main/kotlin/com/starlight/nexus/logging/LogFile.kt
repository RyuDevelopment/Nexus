package com.starlight.nexus.logging

import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class LogFile(val file: File) {

    private var queue: LinkedList<LogEntry> = LinkedList()

    init {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
    }

    fun commit(log: String) {
        queue.add(LogEntry(log))
    }

    @Synchronized
    fun flush() {
        if (queue.isEmpty()) {
            return
        }

        FileOutputStream(file, true).bufferedWriter().use { writer ->
            val iterator = ArrayList(queue).iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                iterator.remove()
                writer.write("[${DATE_FORMAT.format(Date.from(Instant.ofEpochMilli(entry.time)))}] ${entry.log}\n")
            }
        }
    }

    data class LogEntry(val log: String, val time: Long = System.currentTimeMillis())

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")
    }

}
