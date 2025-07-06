package org.incava.mmonkeys.rand

import org.incava.ikdk.util.MapUtil

open class CharIntMap3 {
    private var dists3: Dists3? = null
    val map =  LinkedHashMap<Char, MutableMap<Char, MutableMap<Char, Int>>>()

    fun fetch(x: Char, y: Char, z: Char): Int? {
        return map[x]?.get(y)?.get(z)
    }

    fun increment(x: Char, y: Char, z: Char) {
        MapUtil.ensureMap(map, x)
            .also { map1 ->
                MapUtil.ensureMap(map1, y)
                    .also { map2 ->
                        map2.compute(z) { _, value -> if (value == null) 1 else value + 1 }
                    }
            }

    }

    private fun dists(): Dists3 {
        if (dists3 == null)
            dists3 = Dists3(map)
        return dists3 as Dists3
    }

    fun nextRandomChar(): Char = dists().nextRandomChar()

    fun nextRandomChar(firstChar: Char) = dists().nextRandomChar(firstChar)

    fun nextRandomChar(firstChar: Char, secondChar: Char) = dists().nextRandomChar(firstChar, secondChar)

    fun nextDistributedChar(): Char = dists().nextDistributedChar()

    fun nextDistributedChar(firstChar: Char) = dists().nextDistributedChar(firstChar)

    fun nextDistributedChar(firstChar: Char, secondChar: Char) = dists().nextDistributedChar(firstChar, secondChar)
}