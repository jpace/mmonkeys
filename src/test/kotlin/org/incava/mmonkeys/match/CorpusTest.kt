package org.incava.mmonkeys.match

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class CorpusTest {
    @TestFactory
    fun `given a set of words, hasWord should match`() =
        listOf(
            "abc" to (true to 0),
            "def" to (true to 1),
            "xyz" to (false to -1),
        ).map { (word, expected) ->
            DynamicTest.dynamicTest("given $word, " +
                    "when running the matcher, " +
                    "then the result should be \"$expected\"") {
                val obj = Corpus("abc", "def", "ghi")
                val result = obj.hasWord(word)
                // separate lines to show which element of the pair didn't match
                assertEquals(expected.first, result.first) { "word: $word" }
                assertEquals(expected.second, result.second) { "word: $word" }
            }
        }

    @Test
    fun getWords() {
        val obj = Corpus("abc", "def", "ghi")
        val words = obj.words
        assertEquals(listOf("abc", "def", "ghi"), words)
    }
}