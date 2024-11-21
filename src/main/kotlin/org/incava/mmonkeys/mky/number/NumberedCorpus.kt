package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.IndexedCorpus

class NumberedCorpus(words: List<String>) : Corpus(words) {
    val indexedCorpus: IndexedCorpus<Long> = IndexedCorpus(words, StringEncoder::encodeToLong)

    fun matched(number: Long, length: Int): Int {
        return indexedCorpus.matched(number, length).also { index -> setMatched(index) }
    }

    fun longsForLength(length: Int) : MutableMap<Long, MutableList<Int>>? {
        return indexedCorpus.elements[length]
    }
}