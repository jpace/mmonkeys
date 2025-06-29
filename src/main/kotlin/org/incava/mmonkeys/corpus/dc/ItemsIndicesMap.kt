package org.incava.mmonkeys.corpus.dc

// indexed by length to item to indices as a word
class ItemsIndicesMap<T> {
    // HashMap consumes less memory than the default LinkedHashMap:
    val elements: MutableMap<Int, MutableMap<T, MutableList<Int>>> = hashMapOf()

    fun add(length: Int, item: T, index: Int) {
        elements.computeIfAbsent(length) { hashMapOf() }
            .computeIfAbsent(item) { mutableListOf() }.also { it.add(index) }
    }

    fun itemsForLength(length: Int): Map<T, List<Int>>? {
        return elements[length]
    }

    fun hasForLength(length: Int): Boolean {
        return elements.containsKey(length)
    }

    fun getIndex(item: T, length: Int): Int {
        val forLength: MutableMap<T, MutableList<Int>> = elements[length] ?: return -1
        val forEncoded: MutableList<Int> = forLength[item] ?: return -1
        // this is the index into the corpus words
        return forEncoded[0]
    }

    fun removeItem(item: T, length: Int) {
        val forLength: MutableMap<T, MutableList<Int>> = elements[length] ?: return
        val forItem: MutableList<Int> = forLength[item] ?: return
        forItem.removeAt(0)
        if (forItem.isEmpty()) {
            forLength.remove(item)
        }
        if (forLength.isEmpty()) {
            elements.remove(length)
        }
    }
}