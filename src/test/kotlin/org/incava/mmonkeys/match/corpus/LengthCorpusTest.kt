package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class LengthCorpusTest {

    @Test
    fun soughtByLength() {
        val input = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = LengthCorpus(input)
        val expected = mapOf(
            2 to listOf("ab", "cd"),
            3 to listOf("def", "ghi"),
            4 to listOf("defg"),
            5 to listOf("lmnop"),
        )
        val result = obj.soughtByLength
        assertEquals(expected, result)
    }

    @Test
    fun matched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = LengthCorpus(input)
        Console.info("obj.class", obj.javaClass)
        Console.info("obj", obj)
        obj.matched("ab", 0, 2)
        assertEquals(setOf(0), obj.matched)
        assertEquals(listOf("cd"), obj.soughtByLength[2]?.toList())
        obj.matched("cd", 0, 2)
        assertEquals(setOf(0, 1), obj.matched)
        assertNull(obj.soughtByLength[2])
    }
}