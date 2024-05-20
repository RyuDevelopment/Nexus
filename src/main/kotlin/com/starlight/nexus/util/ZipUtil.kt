package com.starlight.nexus.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object ZipUtil {

    @Throws(IOException::class)
    fun zip(sourcDirPath: String,zipPath: String):File {

        val zipFile: Path = Files.createFile(Paths.get(zipPath))
        val sourceDirPath: Path = Paths.get(sourcDirPath)

        ZipOutputStream(Files.newOutputStream(zipFile)).use { zipOutputStream ->
            Files.walk(sourceDirPath).use { paths ->
                paths
                    .filter { path -> !Files.isDirectory(path) }
                    .forEach {
                        val zipEntry = ZipEntry(sourceDirPath.relativize(it).toString())
                        try {
                            zipOutputStream.putNextEntry(zipEntry)
                            Files.copy(it, zipOutputStream)
                            zipOutputStream.closeEntry()
                        } catch (ex: IOException) {
                            ex.printStackTrace()
                        }
                    }
            }
        }

        return zipFile.toFile()
    }

    fun unZip(file: File,destination: File) {

        val buffer = ByteArray(1024)
        val stream = ZipInputStream(FileInputStream(file))

        var entry = stream.nextEntry

        while (entry != null) {

            val newFile = this.createFile(destination,entry)

            if (entry.isDirectory) {

                if (!newFile.isDirectory && !newFile.mkdirs()) {
                    throw IOException("Failed to create directory $newFile whilst unzipping ${file.name}")
                }

            } else {

                val parent = newFile.parentFile

                if (!parent.isDirectory && !parent.mkdirs()) {
                    throw IOException("Failed to create directory $parent")
                }

                val outputStream = FileOutputStream(newFile)

                var len: Int

                while (stream.read(buffer).also{len = it} > 0) {
                    outputStream.write(buffer,0,len)
                }

                outputStream.close()
            }

            entry = stream.nextEntry
        }

        stream.closeEntry()
        stream.close()
    }

    private fun createFile(destination: File,entry: ZipEntry): File {

        val toReturn = File(destination,entry.name)

        val path = destination.canonicalPath
        val destinationPath = toReturn.canonicalPath

        if (!destinationPath.startsWith("$path${File.separator}")) {
            throw IOException("Entry is outside of the target dir: " + entry.name)
        }

        return toReturn
    }

}