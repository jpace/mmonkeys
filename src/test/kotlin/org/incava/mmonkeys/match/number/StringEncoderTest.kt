package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class StringEncoderTest {
    private val intValues = listOf(
        "a" to 26,
        "z" to 51,
        "aa" to 676,
        "ab" to 677,
        "it" to 903,
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

    @TestFactory
    fun `given a string, the encoded int value should be`() =
        intValues.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, when encoding to an integer, the result should be $expected") {
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

    @TestFactory
    fun `given a long string, it should encode and decode into its original`() =
        longValues.map { (str, _) ->
            DynamicTest.dynamicTest("given $str of size ${str.length}, " +
                    "when encoding then decoding, " +
                    "then the result should be the original") {
                val encoded = StringEncoder.encodeToLong(str)
                Console.info("str", str)
                Console.info("encoded", encoded)
                val result = StringEncoder.decode(encoded)
                Console.info("result", result)
                assertEquals(str, result)
            }
        }

    @TestFactory
    fun `given a short string, it should encode and decode into its original`() =
        intValues.map { (str, _) ->
            DynamicTest.dynamicTest("given $str of size ${str.length}, " +
                    "when encoding then decoding, " +
                    "then the result should be the original") {
                val encoded = StringEncoder.encodeToInt(str)
                Console.info("str", str)
                Console.info("encoded", encoded)
                val result = StringEncoder.decode(encoded)
                Console.info("result", result)
                assertEquals(str, result)
            }
        }

    @TestFactory
    fun decode() =
        listOf(
            26L to "a",
            676L to "aa",
            677L to "ab",
            678L to "ac",
            903L to "it"
        ).map { (number, str) ->
            DynamicTest.dynamicTest("number: $number, str: $str") {
                Console.info("number", number)
                val result = StringEncoder.decode(number)
                Console.info("result", result)
                Console.info("")
                assertEquals(str, result)
            }
        }


    @TestFactory
    fun encodeToInt() =
        listOf(
            26 to "a",
            27 to "b",
            676 to "aa",
            677 to "ab",
            677 to "ab",
            903L to "it"
        ).map { (number, str) ->
            DynamicTest.dynamicTest("number: $number, str: $str") {
                val encoded = StringEncoder.encodeToInt(str)
                Console.info("number", number)
                Console.info("encoded", encoded)
                val result = StringEncoder.decode(encoded)
                Console.info("result", result)
                assertEquals(str, result)
            }
        }

    @TestFactory
    fun encodeToLong() =
        listOf(
            903L to "aa",
            903L to "ab",
            903L to "it"
        ).map { (number, str) ->
            DynamicTest.dynamicTest("number: $number, str: $str") {
                val encoded = StringEncoder.encodeToLong(str)
                Console.info("number", number)
                Console.info("encoded", encoded)
                val result = StringEncoder.decode(encoded)
                Console.info("result", result)
                assertEquals(str, result)
            }
        }

    @TestFactory
    fun encodeToLongThenDecode() =
        listOf(
            903L to "it"
        ).map { (number, str) ->
            DynamicTest.dynamicTest("number: $number, str: $str") {
                val encoded = StringEncoder.encodeToLong(str)
                Console.info("number", number)
                Console.info("encoded", encoded)
                val result = StringEncoder.decode(encoded)
                Console.info("result", result)
                assertEquals(str, result)
            }
        }
}