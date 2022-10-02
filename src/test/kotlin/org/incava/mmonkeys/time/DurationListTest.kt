package org.incava.mmonkeys.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Duration

internal class DurationListTest {
    @ParameterizedTest
    @MethodSource("dataForAverage")
    fun average(expected: Duration, args: Collection<Duration>) {
        val obj = DurationList(args)
        val result = obj.average()
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForAverage(): List<Arguments> {
            val sublists = mutableListOf<Arguments>()
            sublists += Arguments.of(Duration.ofMillis(30), listOf(Duration.ofMillis(5), Duration.ofMillis(25), Duration.ofMillis(60)))
            sublists += Arguments.of(Duration.ofMillis(0), emptyList<Duration>())
            return sublists
        }
    }
}