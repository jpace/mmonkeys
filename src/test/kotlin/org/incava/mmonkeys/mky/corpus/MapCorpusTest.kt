package org.incava.mmonkeys.mky.corpus

import org.incava.ikdk.io.Console
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class MapCorpusTest {
    @Test
    fun lengthToStringsToIndices() {
        val input = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = MapCorpus(input)
        val expected = mapOf(
            2 to mapOf("ab" to listOf(0), "cd" to listOf(1)),
            3 to mapOf("def" to listOf(2), "ghi" to listOf(4)),
            2 to mapOf("ab" to listOf(0), "cd" to listOf(1)),
            4 to mapOf("defg" to listOf(3)),
            5 to mapOf("lmnop" to listOf(5)),
        )
        val result = obj.lengthToStringsToIndices
        assertEquals(expected, result)
    }

    @Test
    fun matched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = MapCorpus(input)
        Console.info("obj.class", obj.javaClass)
        Console.info("obj", obj)
        obj.matched("ab", 2)
        assertEquals(setOf(0), obj.matched)
        val result = obj.lengthToStringsToIndices[2]
        Console.info("result", result)
        assertEquals(mapOf("cd" to listOf(1)), obj.lengthToStringsToIndices[2])
        obj.matched("cd", 2)
        assertEquals(setOf(0, 1), obj.matched)
        assertNull(obj.lengthToStringsToIndices[2])
    }
}