package org.incava.mmonkeys.rand

import org.incava.ikdk.util.MapUtil

open class CharIntMap3 : LinkedHashMap<Char, MutableMap<Char, MutableMap<Char, Int>>>() {
    private var dists3: Dists3? = null
    private var random3: Random3? = null

    fun fetch(x: Char, y: Char, z: Char): Int? {
        return this[x]?.get(y)?.get(z)
    }

    fun increment(x: Char, y: Char, z: Char) {
        MapUtil.ensureMap(this, x)
            .also { map1 ->
                MapUtil.ensureMap(map1, y)
                    .also { map2 ->
                        map2.compute(z) { _, value -> if (value == null) 1 else value + 1 }
                    }
            }

    }

    fun dists(): Dists3 {
        if (dists3 == null)
            dists3 = Dists3(this)
        return dists3 as Dists3
    }

    fun random(): Random3 {
        if (random3 == null)
            random3 = Random3(this)
        return random3 as Random3
    }
}