package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.number.StringEncoder

class DualCorpus(words: List<String>) : Corpus(words) {
    val stringCorpus: SingleCorpus<String> = SingleCorpus()
    val encodedCorpus: SingleCorpus<Long> = SingleCorpus()

    init {
        words.withIndex().forEach { (index, word) ->
            // oh, the delicious irony that long words remain strings, and short words become longs.
            if (word.length > 13) {
                stringCorpus.add(word, word.length, index)
            } else {
                val encoded = StringEncoder.encodeToLong(word)
                encodedCorpus.add(encoded, word.length, index)}

        }
    }

    fun stringsForLength(length: Int): MutableMap<String, MutableList<Int>>? {
        return stringCorpus.elements[length]
    }

    fun longsForLength(length: Int) : MutableMap<Long, MutableList<Int>>? {
        return encodedCorpus.elements[length]
    }

    fun matched(word: String, length: Int): Int {
        return stringCorpus.matched(word, length).also { index -> removeAt(index) }
    }

    fun matched(encoded: Long, length: Int): Int {
        return encodedCorpus.matched(encoded, length).also { index -> removeAt(index) }
    }
}