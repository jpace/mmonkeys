package org.incava.mmonkeys.mky.corpus

import java.io.File
import kotlin.math.min

object CorpusFactory {
    fun readFileWords(file: File, numLines: Int, wordSizeLimit: Int): List<String> {
        val lines = file.readText().split("\r\n")
        val sonnet = lines.subList(0, min(numLines, lines.size))

        // I forgot numbers.
        return sonnet.joinToString()
            .split(Regex(" +"))
            .map(String::toLowerCase)
            .map { it.replace(Regex("[^a-z+]"), "") }
            .filter { it.length in 1..wordSizeLimit }
    }

    fun <T : Corpus> createCorpus(fileName: String, numLines: Int, wordSizeLimit: Int, ctor: (List<String>) -> T): T {
        val words = readFileWords(File(fileName), numLines, wordSizeLimit)
        return ctor(words)
    }

    fun <T : Corpus> createCorpus(file: File, numLines: Int, wordSizeLimit: Int, ctor: (List<String>) -> T): T {
        val words = readFileWords(file, numLines, wordSizeLimit)
        return ctor(words)
    }
}