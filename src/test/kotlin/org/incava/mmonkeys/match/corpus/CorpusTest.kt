package org.incava.mmonkeys.match.corpus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class CorpusTest {
    @TestFactory
    fun `given a set of words, match should return`() =
        listOf(
            "abc" to 0,
            "def" to 1,
            "xyz" to -1,
        ).map { (word, expected) ->
            DynamicTest.dynamicTest("given $word, " +
                    "when running the matcher, " +
                    "then the result should be \"$expected\"") {
                val obj = Corpus(listOf("abc", "def", "ghi"))
                val result = obj.match(word)
                // separate lines to show which element of the pair didn't match
                assertEquals(expected, result) { "word: $word" }
            }
        }

    @Test
    fun getWords() {
        val obj = Corpus(listOf("abc", "def", "ghi"))
        val words = obj.words
        assertEquals(listOf("abc", "def", "ghi"), words)
    }
}