package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.util.MapUtil

class Sequences(words: List<String>) {
    val twos: Map<Char, MutableMap<Char, Int>>
    val threes: Map<Char, MutableMap<Char, MutableMap<Char, Int>>>

    init {
        val chars = mutableListOf<Char>()
        words.forEach { word ->
            word.forEach { chars += it.lowercaseChar() }
            chars += ' '
        }
        twos = mutableMapOf()
        threes = mutableMapOf()
        (1 until chars.size).forEach { index ->
            val prev = chars[index - 1]
            val curr = chars[index]
            addToMap(twos, prev, curr)
           if (index > 1) {
                val prevPrev = chars[index - 2]
                threes.computeIfAbsent(prevPrev) { mutableMapOf() }
                    .also { seconds ->
                        addToMap(seconds, prev, curr)
                    }
            }
        }
    }

    fun addToMap(map: MutableMap<Char, MutableMap<Char, Int>>, prevChar: Char, currChar: Char) {
        map.computeIfAbsent(prevChar) { mutableMapOf() }
            .also {
                MapUtil.increment(it, currChar)
            }
    }
}