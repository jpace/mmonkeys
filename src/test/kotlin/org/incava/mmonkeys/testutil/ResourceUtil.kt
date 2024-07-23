package org.incava.mmonkeys.testutil

import java.io.File

object ResourceUtil {
    fun getResourceFile(name: String): File {
        val resource = ResourceUtil::class.java.classLoader.getResource(name) ?: throw RuntimeException("no such file: $name")
        return File(resource.file)
    }
}