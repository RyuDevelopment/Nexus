package com.starlight.nexus.util

import com.starlight.nexus.Nexus
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object Tasks {

    @JvmStatic
    fun sync(lambda: () -> Unit): BukkitTask {
        return Nexus.instance.server.scheduler.runTask(Nexus.instance) {
            lambda.invoke()
        }
    }

    @JvmStatic
    fun delayed(delay: Long, lambda: () -> Unit): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskLater(Nexus.instance, {
            lambda.invoke()
        }, delay)
    }

    @JvmStatic
    fun delayed(delay: Long, runnable: Runnable): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskLater(Nexus.instance, runnable, delay)
    }

    @JvmStatic
    fun delayed(delay: Long, runnable: BukkitRunnable): BukkitTask {
        return runnable.runTaskLater(Nexus.instance, delay)
    }

    @JvmStatic
    fun timer(delay: Long, interval: Long, lambda: () -> Unit): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskTimer(Nexus.instance, {
            lambda.invoke()
        }, delay, interval)
    }

    @JvmStatic
    fun timer(delay: Long, interval: Long, runnable: Runnable): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskTimer(Nexus.instance, runnable, delay, interval)
    }

    @JvmStatic
    fun timer(delay: Long, interval: Long, runnable: BukkitRunnable): BukkitTask {
        return runnable.runTaskTimer(Nexus.instance, delay, interval)
    }

    @JvmStatic
    fun async(lambda: () -> Unit): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskAsynchronously(Nexus.instance) {
            lambda.invoke()
        }
    }

    @JvmStatic
    fun asyncDelayed(delay: Long, lambda: () -> Unit): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskLaterAsynchronously(Nexus.instance, {
            lambda.invoke()
        }, delay)
    }

    @JvmStatic
    fun asyncDelayed(runnable: Runnable, delay: Long): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskLaterAsynchronously(Nexus.instance, runnable, delay)
    }

    @JvmStatic
    fun asyncDelayed(runnable: BukkitRunnable, delay: Long): BukkitTask {
        return runnable.runTaskLaterAsynchronously(Nexus.instance, delay)
    }

    @JvmStatic
    fun runAsyncLater(callable: Runnable, delay: Long) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Nexus.instance, callable, delay)
    }

    @JvmStatic
    fun runLater(callable: Runnable, delay: Long) {
        Bukkit.getScheduler().runTaskLater(Nexus.instance, callable, delay)
    }

    fun runTimer(callable: Runnable, delay: Long, interval: Long) {
        Bukkit.getScheduler().runTaskTimer(Nexus.instance, callable, delay, interval)
    }

    fun runAsyncTimer(callable: Runnable, delay: Long, interval: Long) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Nexus.instance, callable, delay, interval)
    }

    @JvmStatic
    fun asyncTimer(delay: Long, interval: Long, lambda: () -> Unit): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskTimerAsynchronously(Nexus.instance, {
            lambda.invoke()
        }, delay, interval)
    }

    @JvmStatic
    fun asyncTimer(runnable: Runnable, delay: Long, interval: Long): BukkitTask {
        return Nexus.instance.server.scheduler.runTaskTimerAsynchronously(Nexus.instance, runnable, delay, interval)
    }

    @JvmStatic
    fun asyncTimer(runnable: BukkitRunnable, delay: Long, interval: Long): BukkitTask {
        return runnable.runTaskTimerAsynchronously(Nexus.instance, delay, interval)
    }

}