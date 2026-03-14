package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.util.ResourceUtil
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

object CorpusFactory {
    private val filesToWords: MutableMap<File, List<String>> = mutableMapOf()

    fun defaultCorpus() = WordCorpus(fileToWords(ResourceUtil.FULL_FILE))

    fun defaultWords() = fileToWords(ResourceUtil.FULL_FILE)

    fun fileToWords(file: File): List<String> {
        val lines = file.readLines()
        return filesToWords.computeIfAbsent(file) { linesToWords(lines) }
    }

    fun toWords(stream: InputStream): List<String> {
        val lines = ArrayList<String>()
        BufferedReader(InputStreamReader(stream)).forEachLine { lines.add(it) }
        return lines
    }

    private fun linesToWords(lines: List<String>): List<String> {
        return lines
            .withIndex()
            .map { it.value }
            .map { it.trim() }
            .map(String::lowercase)
            // I forgot numbers.
            .map { it.replace(Regex("[^a-z+]"), " ") }
            .flatMap { it.split(Regex("\\s+")) }
            .filterNot { it.isBlank() }
    }
}