package org.incava.mmonkeys.mky.corpus

import java.io.File

object CorpusFactory {
    fun readFileWords(file: File, numLines: Int): List<String> {
        val lines = file.readLines()

        // I forgot numbers.
        return lines
            .withIndex()
            .filter { numLines < 0 || it.index < numLines }
            .map { it.value }
            .map { it.trim() }
            .map(String::lowercase)
            .map { it.replace(Regex("[^a-z+]"), " ") }
            .flatMap { it.split(Regex("\\s+")) }
            .filterNot { it.isBlank() }
    }

    fun readFileWords(file: File): List<String> = readFileWords(file, -1)
}