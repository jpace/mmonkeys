package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder

class DualCorpus(words: List<String>) : Corpus(words) {
    private val stringItems: ItemsIndicesMap<String> = ItemsIndicesMap()
    private val encodedItems: ItemsIndicesMap<Long> = ItemsIndicesMap()

    // @todo - make this based on words (words.max ...), and dynamic
    val maxLength = 27 // "honorificabilitudinitatibus"

    init {
        words.withIndex().forEach { (index, word) ->
            // oh, the delicious irony that long words remain strings, and short words become longs.
            if (word.length > RandEncoded.Constants.MAX_ENCODED_CHARS) {
                stringItems.add(word.length, word, index)
            } else {
                val encoded = StringEncoder.encodeToLong(word)
                encodedItems.add(word.length, encoded, index)
            }
        }
    }

    fun stringsForLength(length: Int): Map<String, List<Int>>? = forLength(stringItems, length)

    fun longsForLength(length: Int): Map<Long, List<Int>>? = forLength(encodedItems, length)

    private fun <T> forLength(items: ItemsIndicesMap<T>, length: Int): Map<T, List<Int>>? {
        return items.itemsForLength(length)
    }

    fun setMatched(word: String, length: Int): Int {
        return setMatched(stringItems, word, length)
    }

    fun setMatched(encoded: Long, length: Int): Int {
        return setMatched(encodedItems, encoded, length)
    }

    fun <T> setMatched(items: ItemsIndicesMap<T>, item: T, length: Int): Int {
        val index = items.getIndex(item, length)
        items.removeItem(item, length)
        matches.setMatched(index)
        return index
    }
}