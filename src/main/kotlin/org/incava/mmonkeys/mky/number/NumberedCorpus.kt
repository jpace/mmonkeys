package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.dc.ItemsIndicesMap

class NumberedCorpus(words: List<String>) : Corpus(words) {
    val indexed: ItemsIndicesMap<Long> = ItemsIndicesMap()

    init {
        words.withIndex().forEach { indexed.add(it.value.length, StringEncoder.encodeToLong(it.value), it.index) }
    }

    fun setMatched(number: Long, length: Int): Int {
        val index = indexed.getIndex(number, length)
        indexed.removeItem(number, length)
        matches.setMatched(index)
        return index
    }

    fun longsForLength(length: Int): Map<Long, List<Int>>? {
        return indexed.itemsForLength(length)
    }

    fun hasForLength(length: Int) = indexed.hasForLength(length)
}