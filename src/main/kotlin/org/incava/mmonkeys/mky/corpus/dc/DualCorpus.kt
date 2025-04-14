package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder

class DualCorpus(words: List<String>) : Corpus(words) {
    private val stringCorpus: IndexedCorpus<String> = IndexedCorpus { it }
    private val encodedCorpus: IndexedCorpus<Long> = IndexedCorpus(StringEncoder::encodeToLong)

    // @todo - make this based on words (words.max ...), and dynamic
    val maxLength = 27 // "honorificabilitudinitatibus"

    init {
        words.withIndex().forEach { (index, word) ->
            // oh, the delicious irony that long words remain strings, and short words become longs.
            val corpus = if (word.length > RandEncoded.Constants.MAX_ENCODED_CHARS) {
                stringCorpus
            } else {
                encodedCorpus
            }
            corpus.add(word, index)
        }
    }

    fun stringsForLength(length: Int): MutableMap<String, MutableList<Int>>? {
        return stringCorpus.elements[length]
    }

    fun longsForLength(length: Int): MutableMap<Long, MutableList<Int>>? {
        return encodedCorpus.elements[length]
    }

    fun setMatched(word: String, length: Int): Int {
        return stringCorpus.setMatched(word, length).also { index -> setMatched(index) }
    }

    fun setMatched(encoded: Long, length: Int): Int {
        return encodedCorpus.setMatched(encoded, length).also { index -> setMatched(index) }
    }
}