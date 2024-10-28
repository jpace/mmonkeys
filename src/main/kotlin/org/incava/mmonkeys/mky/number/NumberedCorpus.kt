package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.IndexedCorpus

class NumberedCorpus(words: List<String>) : Corpus(words) {
    // length to [ encoded to [ indices in sought ] ]
    val numbers: MutableMap<Int, MutableMap<Long, MutableList<Int>>> = mutableMapOf()
    val indexedCorpus: IndexedCorpus<Long> = IndexedCorpus(words, StringEncoder::encodeToLong)

    val rangeEncoded = (1..13).associateWith { length ->
        val encoded = StringEncoder.encodeToLong("a".repeat(length))
        encoded to (encoded + 1) * 26
    }

    init {
        val encoded = mutableMapOf<String, Long>()
        words.withIndex().forEach { word ->
            // trouble with words of size 14; see StringEncodersTest
            val enc = encoded.computeIfAbsent(word.value, StringEncoder::encodeToLong)
            if (enc < 0) {
                Console.info("overflow")
                Console.info("word", word.value)
                Console.info("enc", enc)
            }
            numbers
                .computeIfAbsent(word.value.length) { mutableMapOf() }
                .computeIfAbsent(enc) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(word: String, number: Long, length: Int): Int {
        val forLength = numbers[length] ?: return -1
        val forEncoded = forLength[number] ?: return -1
        // this is the index into sought
        val index = forEncoded.removeAt(0)
        Console.info("index", index)
        removeAt(index)
        if (forEncoded.isEmpty()) {
            forLength.remove(number)
        }
        if (forLength.isEmpty()) {
            numbers.remove(length)
        }
        return index
    }
}