package org.incava.mmonkeys.mky.corpus

import org.incava.ikdk.io.Console

class IndexedCorpus<T>(words: List<String>, supplier: (String) -> T) {
    val elements: MutableMap<Int, MutableMap<T, MutableList<Int>>> = mutableMapOf()

    init {
        words.withIndex().forEach { word ->
            elements
                .computeIfAbsent(word.value.length) { hashMapOf() }
                .computeIfAbsent(supplier(word.value)) { mutableListOf() }.also { it.add(word.index) }
        }
    }

    fun matched(item: T, length: Int): Int {
        val forLength: MutableMap<T, MutableList<Int>> = elements[length] ?: return -1
        val forEncoded: MutableList<Int> = forLength[item] ?: return -1
        // this is the index into sought
        val index = forEncoded.removeAt(0)
        Console.info("index", index)
        if (forEncoded.isEmpty()) {
            forLength.remove(item)
        }
        if (forLength.isEmpty()) {
            elements.remove(length)
        }
        return index
    }
}