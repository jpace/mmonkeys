package org.incava.mmonkeys.corpus

open class Matches {
    val indices = mutableSetOf<Int>()

    fun isMatched(index: Int) = indices.contains(index)

    open fun setMatched(index: Int) = indices.add(index)

    fun count() = indices.size
}