package org.incava.mmonkeys.mky.corpus

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class SingleCorpusTest {
    @Test
    fun lengthToStringsToIndices() {
        val input = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = SingleCorpus<String>()
        input.withIndex().forEach { obj.add(it.value, it.value.length, it.index) }
        val expected = mapOf(
            2 to mapOf("ab" to listOf(0), "cd" to listOf(1)),
            3 to mapOf("def" to listOf(2), "ghi" to listOf(4)),
            2 to mapOf("ab" to listOf(0), "cd" to listOf(1)),
            4 to mapOf("defg" to listOf(3)),
            5 to mapOf("lmnop" to listOf(5)),
        )
        val result = obj.elements
        assertEquals(expected, result)
    }

    @Test
    fun matched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = SingleCorpus<String>()
        input.withIndex().forEach { obj.add(it.value, it.value.length, it.index) }
        obj.matched("ab", 2)
        val expectedMap = mapOf("cd" to listOf(1))
        val resultMap = obj.elements[2] as Map<String, List<Int>>
        assertEquals(expectedMap, resultMap)
        obj.matched("cd", 2)
        assertNull(obj.elements[2])
    }
}