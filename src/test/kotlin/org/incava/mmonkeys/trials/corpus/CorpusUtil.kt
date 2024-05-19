package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.Corpus
import java.net.URL
import kotlin.math.min

object CorpusUtil {
    private fun getResource(name: String): URL {
        return CorpusUtil::class.java.classLoader.getResource(name) ?: throw RuntimeException("no such file: $name")
    }

    fun readFile(name: String, numLines: Int, wordSizeLimit: Int): Corpus {
        val file = getResource(name)
        Console.info("file", file)
        val lines = file.readText().split("\r\n")
        val sonnet = lines.subList(0, min(numLines, lines.size))
        Console.info("sonnet")
        Console.info(sonnet.first())
        Console.info(sonnet.last())

        // I forgot numbers.
        val words = sonnet.joinToString()
            .split(Regex(" +"))
            .map(String::toLowerCase)
            .map { it.replace(Regex("[^a-z+]"), "") }
            .filter { it.length in 1..wordSizeLimit }
        Console.info("words.#", words.size)

        return Corpus(words)
    }
}