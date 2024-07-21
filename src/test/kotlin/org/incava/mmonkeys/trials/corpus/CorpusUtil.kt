package org.incava.mmonkeys.trials.corpus

import org.incava.mmonkeys.mky.corpus.Corpus
import java.net.URL
import kotlin.math.min

object CorpusUtil {
    private fun getResource(name: String): URL {
        return CorpusUtil::class.java.classLoader.getResource(name) ?: throw RuntimeException("no such file: $name")
    }

    private fun readFileWords(name: String, numLines: Int, wordSizeLimit: Int): List<String> {
        val file = getResource(name)
        val lines = file.readText().split("\r\n")
        val sonnet = lines.subList(0, min(numLines, lines.size))

        // I forgot numbers.
        return sonnet.joinToString()
            .split(Regex(" +"))
            .map(String::toLowerCase)
            .map { it.replace(Regex("[^a-z+]"), "") }
            .filter { it.length in 1..wordSizeLimit }
    }

    fun <T: Corpus> toCorpus(fileName: String, numLines: Int, wordSizeLimit: Int, ctor: (List<String>) -> T) : T {
        val words = readFileWords(fileName, numLines, wordSizeLimit)
        return ctor(words)
    }
}