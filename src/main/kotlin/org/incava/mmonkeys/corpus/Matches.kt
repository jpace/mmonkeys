package org.incava.mmonkeys.corpus

class Matches {
    val indices = mutableSetOf<Int>()

    fun isMatched(index: Int) = indices.contains(index)

    fun setMatched(index: Int) = indices.add(index)

    fun count() = indices.size
}