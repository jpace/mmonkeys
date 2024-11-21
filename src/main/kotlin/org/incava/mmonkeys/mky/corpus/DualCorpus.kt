package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder

class DualCorpus(words: List<String>) : Corpus(words) {
    val stringCorpus: SingleCorpus<String> = SingleCorpus()
    val encodedCorpus: SingleCorpus<Long> = SingleCorpus()

    // @todo - make this based on words (words.max ...), and dynamic
    val maxLength = 27 // "honorificabilitudinitatibus"

    init {
        words.withIndex().forEach { (index, word) ->
            // oh, the delicious irony that long words remain strings, and short words become longs.
            if (word.length > RandEncoded.Constants.MAX_ENCODED_CHARS) {
                stringCorpus.add(word, word.length, index)
            } else {
                val encoded = StringEncoder.encodeToLong(word)
                encodedCorpus.add(encoded, word.length, index)
            }
        }
    }

    fun stringsForLength(length: Int): MutableMap<String, MutableList<Int>>? {
        return forLength(stringCorpus, length)
    }

    fun longsForLength(length: Int): MutableMap<Long, MutableList<Int>>? {
        return forLength(encodedCorpus, length)
    }

    private fun <T> forLength(corpus: SingleCorpus<T>, length: Int): MutableMap<T, MutableList<Int>>? {
        return corpus.elements[length]
    }

    fun setMatched(word: String, length: Int): Int {
        return setMatched(stringCorpus, word, length)
    }

    fun setMatched(encoded: Long, length: Int): Int {
        return setMatched(encodedCorpus, encoded, length)
    }

    private fun <T> setMatched(corpus: SingleCorpus<T>, value: T, length: Int) : Int {
        return corpus.setMatched(value, length).also { index -> setMatched(index) }
    }
}