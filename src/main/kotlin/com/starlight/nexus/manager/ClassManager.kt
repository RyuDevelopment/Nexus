package com.starlight.nexus.manager

import com.google.common.collect.ImmutableSet
import com.starlight.nexus.util.time.TimeUtil
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.net.URL
import java.security.CodeSource
import java.text.DecimalFormat
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.logging.Logger

/*
    * Author: T4yrn
    * Project: nexus
    * Date: 14/2/2024 - 16:04
*/

class ClassManager(
    private val plugin: JavaPlugin
) {

    private val logger: Logger = Logger.getLogger(ClassManager::class.java.name)
    private val registeredClasses: MutableSet<Class<*>> = HashSet()

    private fun formatTime(milliseconds: Long): String {
        val df = DecimalFormat("#0.00")
        return df.format(milliseconds / 1000.0)
    }

    private fun getRegisteredClasses(): Set<Class<*>> {
        return ImmutableSet.copyOf(registeredClasses)
    }

    private fun getClassesInPackageJava11(packageName: String): Collection<Class<*>> {
        val classes: MutableCollection<Class<*>> = ArrayList()

        val codeSource: CodeSource = plugin.javaClass.protectionDomain.codeSource
        val resource: URL = codeSource.location
        val relPath: String = packageName.replace('.', '/')
        var resPath: String = resource.path.replace("%20", " ")
        val jarPath: String = resPath.replaceFirst("[.]jar[!].*".toRegex(), ".jar").replaceFirst("file:".toRegex(), "")
        val jarFile: JarFile

        try {
            jarFile = JarFile(jarPath)
        } catch (e: IOException) {
            throw RuntimeException("Unexpected IOException reading JAR File '$jarPath'", e)
        }

        val entries: Enumeration<JarEntry> = jarFile.entries()

        while (entries.hasMoreElements()) {
            val entry: JarEntry = entries.nextElement()
            val entryName: String = entry.name
            var className: String? = null

            if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length > relPath.length + "/".length) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "")
            }

            if (className != null) {
                var clazz: Class<*>? = null

                try {
                    clazz = Class.forName(className)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

                if (clazz != null) {
                    classes.add(clazz)
                }
            }
        }

        try {
            jarFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ImmutableSet.copyOf(classes)
    }

    private fun getClassesInPackageJava8(packageName: String): Collection<Class<*>> {
        val classes: MutableCollection<Class<*>> = ArrayList()

        val codeSource: CodeSource = this.javaClass.protectionDomain.codeSource
        val resource: URL = codeSource.location
        val relPath: String = packageName.replace('.', '/')
        var resPath: String = resource.path.replace("%20", " ")
        val jarPath: String = resPath.replaceFirst("[.]jar[!].*".toRegex(), ".jar").replaceFirst("file:".toRegex(), "")
        val jarFile: JarFile

        try {
            jarFile = JarFile(jarPath)
        } catch (e: IOException) {
            throw RuntimeException("Unexpected IOException reading JAR File '$jarPath'", e)
        }

        val entries: Enumeration<JarEntry> = jarFile.entries()

        while (entries.hasMoreElements()) {
            val entry: JarEntry = entries.nextElement()
            val entryName: String = entry.name
            var className: String? = null

            if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length > relPath.length + "/".length) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "")
            }

            if (className != null) {
                var clazz: Class<*>? = null

                try {
                    clazz = Class.forName(className)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

                if (clazz != null) {
                    classes.add(clazz)
                }
            }
        }

        try {
            jarFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ImmutableSet.copyOf(classes)
    }

    companion object {
        private lateinit var instance: ClassManager

        fun initialize(javaPlugin: JavaPlugin) {
            if (!Companion::instance.isInitialized) {
                instance = ClassManager(javaPlugin)
            }
        }

        fun registerPackage(packageName: String, displayLog: String, log: Boolean) {
            val startTime = System.currentTimeMillis()

            val javaVersion = System.getProperty("java.version")

            if (javaVersion.startsWith("1.8")) {
                val classesInPackage = instance.getClassesInPackageJava8(packageName)
                instance.registeredClasses.addAll(classesInPackage)
            } else {
                val classesInPackage = instance.getClassesInPackageJava11(packageName)
                instance.registeredClasses.addAll(classesInPackage)
            }
            Long.MAX_VALUE
            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startTime

            if (log) {
                instance.logger.info("[${instance.plugin.name}] Successfully registered '${instance.registeredClasses.size}' $displayLog${if (instance.registeredClasses.size != 1) "s" else ""} in ${TimeUtil.formatTime(elapsedTime)} seconds.")
            }
        }
    }
}