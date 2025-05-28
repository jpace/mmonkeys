package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.dc.ItemsIndicesMap
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

class NumberedCorpus(words: List<String>) : Corpus(words) {
    val indexed: ItemsIndicesMap<Long> = ItemsIndicesMap()

    init {
        words.withIndex().forEach { indexed.add(it.value.length, StringEncoder.encodeToLong(it.value), it.index) }
    }

    fun setMatched(number: Long, length: Int): Int {
        val index = indexed.getIndex(number, length)
        Qlog.info("index", index)
        matches.setMatched(index)
        return index
    }

    fun removeItem(number: Long, length: Int) {
        indexed.removeItem(number, length)
    }

    override fun findMatch(word: String): Int? {
        TODO("Not yet implemented")
    }

    fun findMatch(number: Long, length: Int) : Int? {
        val forLength = indexed.itemsForLength(length) ?: return null
        val forEncoded = forLength[number]
        return if (forEncoded.isNullOrEmpty()) {
            null
        } else {
            return forEncoded[0]
        }
    }

    fun longsForLength(length: Int): Map<Long, List<Int>>? {
        return indexed.itemsForLength(length)
    }

    fun hasForLength(length: Int) = indexed.hasForLength(length)
}