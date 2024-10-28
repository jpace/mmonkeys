package org.incava.mmonkeys.mky.corpus

class SingleCorpus<T> {
    // HashMap consumes less memory than the default LinkedHashMap:
    val elements: MutableMap<Int, MutableMap<T, MutableList<Int>>> = hashMapOf()

    fun add(item: T, length: Int, index: Int) {
        elements.computeIfAbsent(length) { hashMapOf() }
            .computeIfAbsent(item) { mutableListOf() }.also { it.add(index) }
    }

    fun matched(item: T, length: Int): Int {
        val forLength: MutableMap<T, MutableList<Int>> = elements[length] ?: return -1
        val forEncoded: MutableList<Int> = forLength[item] ?: return -1
        // this is the index into the corpus words
        val index = forEncoded.removeAt(0)
        if (forEncoded.isEmpty()) {
            forLength.remove(item)
        }
        if (forLength.isEmpty()) {
            elements.remove(length)
        }
        return index
    }
}