package org.incava.mmonkeys.corpus

abstract class WordCorpus(words: List<String>) : Corpus(words) {
    abstract fun findMatch(word: String): Int?

    abstract fun findWord(word: String): Int?
}
