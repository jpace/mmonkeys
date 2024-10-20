package org.incava.mmonkeys.testutil

import java.io.File

object ResourceUtil {
    val FULL_FILE = getResourceFile("pg100.txt")

    fun getResourceFile(name: String): File {
        val resource = ResourceUtil::class.java.classLoader.getResource(name) ?: throw RuntimeException("no such file: $name")
        return File(resource.file)
    }
}