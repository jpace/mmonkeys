package org.incava.mmonkeys.corpus

import java.io.File

object CorpusFactory {
    private val filesToWords: MutableMap<File, List<String>> = mutableMapOf()

    fun fileToWords(file: File): List<String> {
        val lines = file.readLines()
        return filesToWords.computeIfAbsent(file) { linesToWords(lines) }
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