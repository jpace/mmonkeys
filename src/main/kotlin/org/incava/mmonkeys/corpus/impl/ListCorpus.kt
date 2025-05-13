package org.incava.mmonkeys.corpus.impl

import org.incava.mmonkeys.corpus.WordCorpus

open class ListCorpus(words: List<String>) : WordCorpus(words) {
    override fun findMatch(word: String): Int? {
        return words().indices.find { index ->
            wordAtIndex(index) == word && !matches.isMatched(index)
        }
    }

    override fun findWord(word: String): Int? {
        return words().indices.find { index ->
            wordAtIndex(index) == word
        }
    }
}
