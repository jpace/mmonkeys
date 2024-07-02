package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.Corpus

class NumberedCorpus(words: List<String>) : Corpus(words) {
    // length to [ encoded to [ indices in sought ] ]
    val numbers: MutableMap<Int, MutableMap<Long, MutableList<Int>>> = mutableMapOf()

    init {
        Console.info("this", this)
        Console.info("words", words)
        val encoded = mutableMapOf<String, Long>()
        words.withIndex().forEach { word ->
            val enc = encoded.computeIfAbsent(word.value, StringEncoderNew::encodeToLong)
            numbers
                .computeIfAbsent(word.value.length) { mutableMapOf() }
                .computeIfAbsent(enc) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(word: String, number: Long, length: Int) : Int {
        remove(word)
        val forLength = numbers[length] ?: return -1
        val forEncoded = forLength[number] ?: return -1
        val index = forEncoded.removeAt(0)
//        Console.info("index", index)
//        Console.info("word[$index]", words[index])
        // this is the index into sought
        if (forEncoded.isEmpty()) {
            forLength.remove(number)
        }
        if (forLength.isEmpty()) {
            numbers.remove(length)
        }
        return index
    }
}