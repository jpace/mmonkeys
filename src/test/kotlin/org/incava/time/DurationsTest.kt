package org.incava.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Duration

internal class DurationsTest {
    @TestFactory
    fun `given durations, the average should be`() =
        listOf(
            listOf(30L, 5L, 25L, 60L) to 30L,
            emptyList<Long>() to 0L,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", when getting the average, then the result should be \"$expected\"") {
                val argsMillis = input.map(Duration::ofMillis)
                val expMillis = Duration.ofMillis(expected)
                val result = Durations.average(argsMillis)
                assertEquals(expMillis, result)
            }
        }

    @ParameterizedTest
    @MethodSource("dataForMillisToString")
    fun millisToString(expected: String, args: LongArray) {
        // cannot deconstruct a List<T> to (T x, T y, T z)?
        val result = when (args.size) {
            3 -> {
                Durations.millisToString(args[0], args[1], args[2])
            }
            2 -> {
                Durations.millisToString(args[0], args[1])
            }
            else -> {
                Durations.millisToString(args[0])
            }
        }
        assertEquals(expected, result, "args: ${args.toList()}")
    }

    @ParameterizedTest
    @MethodSource("dataForFormatted")
    fun formatted(expected: String, millis: Long, args: LongArray) {
        val obj = Duration.ofMillis(millis)
        val result = when (args.size) {
            2 -> {
                Durations.formatted(obj, args[0], args[1])
            }
            1 -> {
                Durations.formatted(obj, args[0])
            }
            else -> {
                Durations.formatted(obj)
            }
        }
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForMillisToString(): List<Arguments> {
            val sublists = mutableListOf<Arguments>()
            sublists += Arguments.of("100 ms", longArrayOf(100L))
            sublists += Arguments.of("1000 ms", longArrayOf(1000L))
            sublists += Arguments.of("120 s", longArrayOf(1000L * 60 * 2))
            sublists += Arguments.of("120 s", longArrayOf(1000L * 60 * 2, 30L))
            sublists += Arguments.of("2 m", longArrayOf(1000L * 60 * 2, 30L, 2L))
            return sublists
        }

        @JvmStatic
        fun dataForFormatted(): List<Arguments> {
            val sublists = mutableListOf<Arguments>()
            sublists += Arguments.of("100 ms", 100L, longArrayOf())
            sublists += Arguments.of("1000 ms", 1000L, longArrayOf())
            sublists += Arguments.of("2000 ms", 2000L, longArrayOf())
            sublists += Arguments.of("3000 ms", 3000L, longArrayOf())
            sublists += Arguments.of("29000 ms", 29_000L, longArrayOf())
            sublists += Arguments.of("30 s", 30_000L, longArrayOf())
            sublists += Arguments.of("31 s", 31_000L, longArrayOf())
            sublists += Arguments.of("29 s", 29_000L, longArrayOf(29L))
            sublists += Arguments.of("120 s", 1000L * 60 * 2, longArrayOf())
            sublists += Arguments.of("3 m", 1000L * 60 * 3, longArrayOf())
            sublists += Arguments.of("2 m", 1000L * 60 * 2, longArrayOf(30L, 2L))
            return sublists
        }
    }
}