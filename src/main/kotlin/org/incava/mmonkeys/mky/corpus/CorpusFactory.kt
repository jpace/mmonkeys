package org.incava.mmonkeys.mky.corpus

import java.io.File
import kotlin.math.min

object CorpusFactory {
    fun readFileWords(file: File, numLines: Int, wordSizeLimit: Int): List<String> {
        return readFileWords(file, numLines).filter { it.length <= wordSizeLimit }
    }

    fun readFileWords(file: File, numLines: Int): List<String> {
        val lines = file.readLines()
        val sonnet = lines.subList(0, min(numLines, lines.size))

        // I forgot numbers.
        return sonnet
            .map { it.trim() }
            .map(String::lowercase)
            .flatMap { it.split(Regex("\\s+")) }
            .map { it.replace(Regex("[^a-z+]"), "") }
            .filterNot { it.isBlank() }
    }
}