package org.incava.mmonkeys.corpus

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import java.io.File

object CorpusFactory {
    val filesToWords: MutableMap<File, List<String>> = mutableMapOf()

    fun readFileWords(file: File, lineFilter: (Int) -> Boolean = { true }, wordFilter: (String) -> Boolean = { true }): List<String> {
        val lines = file.readLines()
        return readFileWords(lines, lineFilter, wordFilter)
    }

    fun readFileWords(lines: List<String>, lineFilter: (Int) -> Boolean = { true }, wordFilter: (String) -> Boolean = { true }): List<String> {
        return lines
            .withIndex()
            .filter { lineFilter(it.index) }
            .map { it.value }
            .map { it.trim() }
            .map(String::lowercase)
            // I forgot numbers.
            .map { it.replace(Regex("[^a-z+]"), " ") }
            .flatMap { it.split(Regex("\\s+")) }
            .filterNot { it.isBlank() }
            .filter(wordFilter)
    }

    fun readFileWords(file: File): List<String> {
        return filesToWords.computeIfAbsent(file) { Qlog.info("file", file); readFileWords(file, lineFilter = { true }, wordFilter = { true }) }
    }

    fun readFileWords(file: File, wordFilter: (String) -> Boolean): List<String> {
        return readFileWords(file, lineFilter = { true }, wordFilter = wordFilter)
    }

    fun corpusOf(file: File): Corpus = corpusOf(file) { true }

    fun corpusOf(file: File, filter: (String) -> Boolean): Corpus {
        return Corpus(wordsOf(file, filter))
    }

    fun dualCorpusOf(file: File): DualCorpus = dualCorpusOf(file) { true }

    fun dualCorpusOf(file: File, filter: (String) -> Boolean): DualCorpus {
        return DualCorpus(wordsOf(file, filter))
    }

    fun wordsOf(file: File, filter: (String) -> Boolean): List<String> {
        return readFileWords(file).filter(filter)
    }
}