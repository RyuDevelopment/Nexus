package com.starlight.nexus.util

import com.google.common.collect.ImmutableSet
import org.bukkit.plugin.Plugin
import org.reflections.util.ClasspathHelper
import org.reflections.vfs.Vfs
import java.io.IOException

object ClassUtilsJava8 {

    @JvmStatic
    fun getClassesInPackage(plugin: Plugin, packageName: String): Collection<Class<*>> {
        val classes = HashSet<Class<*>>()
        for (url in ClasspathHelper.forClassLoader(
            ClasspathHelper.contextClassLoader(),
            ClasspathHelper.staticClassLoader(),
            plugin.javaClass.classLoader
        )) {
            if (!url.file.startsWith(plugin.javaClass.protectionDomain.codeSource.location.file)) {
                continue
            }
            val dir = Vfs.fromURL(url)
            try {
                for (file in dir.files) {
                    val name = file.relativePath.replace("/", ".").replace(".class", "")
                    if (name.startsWith(packageName)) {
                        classes.add(Class.forName(name))
                    }
                }
            } catch (ex: ClassNotFoundException) {
                ex.printStackTrace()
            } catch (ex: IOException) {
                ex.printStackTrace()
            } finally {
                dir.close()
            }
        }
        return ImmutableSet.copyOf(classes)
    }

}