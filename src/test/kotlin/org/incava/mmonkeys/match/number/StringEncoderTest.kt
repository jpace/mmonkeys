package org.incava.mmonkeys.match.number

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class StringEncoderTest {
    private val intValues = listOf(
        "a" to 26,
        "z" to 51,
        "aa" to 676,
        "zz" to 1351,
        "zzzzzz" to 617831551,
    )
    private val longValues = listOf(
        "a" to 26L,
        "zzzzzz" to 617831551L,
        "aaaaaaa" to 8031810176L,
        "zzzzzzz" to 16063620351L,
        "aaaaaaaa" to 208827064576L,
        "aaaaaaaaaaaaa" to 2481152873203736576L,
        "zzzzzzzzzzzzz" to 4962305746407473151L,
    )

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @TestFactory
    fun `given a string, the encoded int value should be`() =
        intValues.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when encoding the value to an integer, " +
                    "then the result should be $expected") {
                val result = StringEncoder.encodeToInt(input)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given a string, the encoded long value should be`() =
        longValues.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input of size ${input.length}, " +
                    "when encoding the value to a long, " +
                    "then the result should be $expected") {
                val result = StringEncoder.encodeToLong(input)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given a string, the encoded numeric value should be`() =
        listOf(
            "a" to 26,
            "zzzzzz" to 617831551,
            "aaaaaaa" to 8031810176L,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given $input of size ${input.length}, " +
                    "when encoding the value to a long, " +
                    "then the result should be $expected") {
                val result = StringEncoder.encode(input)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given an integer, the decoded value should be`() =
        intValues.map { (expected, input) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when decoding the value from an integer, " +
                    "then the result should be $expected") {
                val result = StringEncoder.decode(input)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given a long, the decoded value should be`() =
        longValues.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input of size ${input.length}, " +
                    "when decoding the value from a long, " +
                    "then the result should be $expected") {
                val result = StringEncoder.encodeToLong(input)
                assertEquals(expected, result)
            }
        }
}