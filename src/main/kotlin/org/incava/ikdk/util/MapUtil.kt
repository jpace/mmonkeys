package org.incava.ikdk.util

object MapUtil {
    fun <K> increment(map: MutableMap<K, Int>, key: K) {
        map.compute(key) { _, value -> if (value == null) 1 else value + 1 }
    }
}