package org.incava.mmonkeys.rand

import org.incava.ikdk.util.MapUtil

open class Map3<T, U> : LinkedHashMap<T, MutableMap<T, MutableMap<T, U>>>() {
    fun fetch(x: T, y: T, z: T): U? {
        return this[x]?.get(y)?.get(z)
    }

    fun add(x: T, y: T, z: T, item: U) {
        MapUtil.ensureMap(this, x)
            .also { map1 ->
                MapUtil.ensureMap(map1, y)
                    .also { map2 ->
                        map2[z] = item
                    }
            }
    }
}