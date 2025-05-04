package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.DefaultTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.words.Attempt
import org.junit.jupiter.api.Assertions
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
        val result = obj.indexedCorpus.elements
        assertEquals(expected, result)
    }

    @Test
    fun matched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = MapCorpus(input)
        obj.setMatched("ab")
        assertEquals(setOf(0), obj.matched)
        val expectedMap = mapOf("cd" to listOf(1))
        val resultMap = obj.forLength(2) as Map<String, List<Int>>
        assertEquals(expectedMap, resultMap)
        obj.setMatched("cd")
        assertEquals(setOf(0, 1), obj.matched)
        assertNull(obj.forLength(2))
    }

    @Test
    fun sharedCorpus() {
        val input = listOf("this", "test", "is", "no", "test")
        val corpus = MapCorpus(input)
        val strategy1 = RandomStrategy(Keys.fullList())
        val checker1 = MapWordChecker(corpus)
        val checker2 = MapWordChecker(corpus)
        val mgr1 = DefaultMonkeyManager(corpus)
        val monkey1 = mgr1.createMonkey(strategy1)
        var result: Attempt
        do {
            result = monkey1.runAttempt()
            if (result.hasMatch())
                Qlog.info("result", result)
        } while (!result.hasMatch())
        Assertions.assertEquals(checker1.corpus.indexedCorpus.elements, checker2.corpus.indexedCorpus.elements)
    }
}