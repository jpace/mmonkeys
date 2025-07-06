package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.util.MapUtil

class Sequences(chars: List<Char>) {
    val twos: MutableMap<Char, MutableMap<Char, Int>> = mutableMapOf()
    val threes = CharIntMap3()

    init {
        Qlog.info("chars.#", chars.size)
        (1 until chars.size).forEach { index ->
            val prev = chars[index - 1]
            val curr = chars[index]
            MapUtil.ensureMap(twos, prev)
                .also {
                    MapUtil.increment(it, curr)
                }
            if (index > 1) {
                val prevPrev = chars[index - 2]
                threes.increment(prevPrev, prev, curr)
            }
        }
    }
}
