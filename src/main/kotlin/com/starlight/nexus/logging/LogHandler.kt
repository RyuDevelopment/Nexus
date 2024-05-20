package com.starlight.nexus.logging

import com.starlight.nexus.util.Tasks
import java.util.concurrent.ConcurrentHashMap

object LogHandler {

    private var logFiles: MutableSet<LogFile> = ConcurrentHashMap.newKeySet()

    fun initialLoad() {
        Tasks.asyncTimer(20L * 30L, 20L * 30L) {
            for (logFile in logFiles) {
                logFile.flush()
            }
        }
    }

    fun onDisable() {
        for (logFile in logFiles) {
            logFile.flush()
        }
    }

    fun trackLogFile(logFile: LogFile) {
        logFiles.add(logFile)
    }

    fun forgetLogFile(logFile: LogFile) {
        Tasks.async {
            logFile.flush()
        }

        logFiles.remove(logFile)
    }

}
