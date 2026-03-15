package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.util.ResourceUtil
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
        val words = ArrayList<String>()
        BufferedReader(InputStreamReader(stream)).lines().forEach { words += lineToWords(it) }
        return words
    }

    private fun linesToWords(lines: List<String>): List<String> {
        return lines.fold(mutableListOf()) { list, line -> list += lineToWords(line); list }
    }

    private fun lineToWords(line: String): List<String> {
        // I forgot numbers.
        return line
            .trim()
            .lowercase()
            .replace(Regex("[^a-z+]"), " ")
            .split(Regex("\\s+"))
            .filterNot { it.isBlank() }
    }
}