package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.dc.IndexedCorpus

class MapCorpus(words: List<String>) : Corpus(words) {
    val indexedCorpus: IndexedCorpus<String> = IndexedCorpus(words) { it }

    fun setMatched(word: String): Int {
        return indexedCorpus.setMatched(word, word.length).also { index -> super.setMatched(index) }
    }

    fun forLength(length: Int) = indexedCorpus.elementsForLength(length)
}