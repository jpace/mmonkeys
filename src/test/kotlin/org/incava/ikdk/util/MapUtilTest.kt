package org.incava.ikdk.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MapUtilTest {
    @Test
    fun increment() {
        val map = HashMap<Int, Int>()
        MapUtil.increment(map, 3)
        assertEquals(mapOf(3 to 1), map)
        MapUtil.increment(map, 3)
        assertEquals(mapOf(3 to 2), map)
    }
}