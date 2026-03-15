package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.util.MapUtil

class Sequences(chars: List<Char>) {
    val twos: MutableMap<Char, MutableMapCharToCount> = mutableMapOf()
    val threes = LinkedHashMap<Char, MutableMap<Char, MutableMapCharToCount>>()

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
                MapUtil.ensureMap(threes, prevPrev)
                    .also { map1 ->
                        MapUtil.ensureMap(map1, prev)
                            .also { map2 ->
                                map2.compute(curr) { _, value -> if (value == null) 1 else value + 1 }
                            }
                    }
            }
        }
    }
}
