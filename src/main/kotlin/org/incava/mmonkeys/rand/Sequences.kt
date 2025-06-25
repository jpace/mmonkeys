package org.incava.mmonkeys.rand

import org.incava.ikdk.util.MapUtil

class Sequences(chars: List<Char>) {
    val twos: MutableMap<Char, MutableMap<Char, Int>> = mutableMapOf()
    val threes = CharMap3<Int>()

    init {
        (1 until chars.size).forEach { index ->
            val prev = chars[index - 1]
            val curr = chars[index]
            addToMap(twos, prev, curr)
            if (index > 1) {
                val prevPrev = chars[index - 2]
                val count = (threes.fetch(prevPrev, prev, curr) ?: 0) + 1
                threes.add(prevPrev, prev, curr, count)
            }
        }
    }

    private fun addToMap(map: MutableMap<Char, MutableMap<Char, Int>>, prevChar: Char, currChar: Char) {
        MapUtil.ensureMap(map, prevChar)
            .also {
                MapUtil.increment(it, currChar)
            }
    }
}
