package org.incava.mmonkeys.mky.corpus

import java.io.File
import java.util.*
import kotlin.math.min

object CorpusFactory {
    fun readFileWords(file: File, numLines: Int, wordSizeLimit: Int): List<String> {
        return readFileWords(file, numLines).filter { it.length <= wordSizeLimit }
    }

    fun readFileWords(file: File, numLines: Int): List<String> {
        val lines = file.readText().split("\r\n")
        val sonnet = lines.subList(0, min(numLines, lines.size))

        // I forgot numbers.
        return sonnet.joinToString()
            .split(Regex(" +"))
            .map(String::lowercase)
            .map { it.replace(Regex("[^a-z+]"), "") }
    }

    fun <T : Corpus> createCorpus(file: File, numLines: Int, wordSizeLimit: Int, ctor: (List<String>) -> T): T {
        val words = readFileWords(file, numLines, wordSizeLimit)
        return ctor(words)
    }
}