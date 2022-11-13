package org.incava.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.Duration

internal class DurationListTest {
    @TestFactory
    fun `given durations, the average should be`() =
        listOf(
            listOf(30L, 5L, 25L, 60L) to 30L,
            emptyList<Long>() to 0L,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", when getting the average, then the result should be \"$expected\"") {
                val argsMillis = input.map(Duration::ofMillis)
                val expMillis = Duration.ofMillis(expected)
                val obj = DurationList(argsMillis)
                val result = obj.average()
                assertEquals(expMillis, result)
            }
        }
}