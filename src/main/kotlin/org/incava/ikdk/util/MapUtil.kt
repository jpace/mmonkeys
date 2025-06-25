package org.incava.ikdk.util

object MapUtil {
    fun <K> increment(map: MutableMap<K, Int>, key: K) {
        map.compute(key) { _, value -> if (value == null) 1 else value + 1 }
    }

    fun <K1, K2, V> ensureMap(map: MutableMap<K1, MutableMap<K2, V>>, key: K1): MutableMap<K2, V> {
        return map.computeIfAbsent(key) { mutableMapOf() }
    }

    fun <K1, K2, V> ensureMap(map: HashMap<K1, MutableMap<K2, V>>, key: K1): MutableMap<K2, V> {
        return map.computeIfAbsent(key) { mutableMapOf() }
    }
}