package org.incava.mmonkeys.mky.corpus

class MapCorpus(words: List<String>) : Corpus(words) {
    val indexedCorpus: IndexedCorpus<String> = IndexedCorpus(words) { it }

    fun matched(word: String, length: Int): Int {
        // this is the index into sought
        return indexedCorpus.matched(word, length).also { index -> setMatched(index) }
    }

    fun forLength(length: Int) = indexedCorpus.elements[length]
}