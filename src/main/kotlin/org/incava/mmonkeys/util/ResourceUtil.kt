package org.incava.mmonkeys.util

import java.io.File
import java.io.InputStream

object ResourceUtil {
    val FULL_FILE = getResourceFile("pg100.txt")

    fun getResourceFile(name: String): File {
        val resource = ResourceUtil::class.java.classLoader.getResource(name) ?: throw RuntimeException("no such file: $name")
        return File(resource.file)
    }

    fun getResourceStream(name: String): InputStream {
        return ResourceUtil::class.java.classLoader.getResourceAsStream(name) ?: throw RuntimeException("no such file: $name")
    }
}