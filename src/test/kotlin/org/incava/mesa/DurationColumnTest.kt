package org.incava.mesa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.Duration
import java.time.temporal.ChronoUnit

internal class DurationColumnTest {
    private fun duration(hours: Long?, minutes: Long?, seconds: Long?, millis: Long?): Duration {
        val values = mapOf(
            ChronoUnit.HOURS to hours,
            ChronoUnit.MINUTES to minutes,
            ChronoUnit.SECONDS to seconds,
            ChronoUnit.MILLIS to millis,
        )
        return values.keys.fold(Duration.ZERO) { dur, unit ->
            val value = values[unit]
            if (value == null) dur else dur.plus(value, unit)
        }
    }

    @TestFactory
    fun `given a duration and format, formatCell should return`() =
        listOf(
            duration(1, 20, null, 300) to "1:20:00.300",
            duration(1, 20, null, 30) to "1:20:00.030",
            duration(1, 20, null, 3) to "1:20:00.003",
            duration(null, 20, null, null) to "20:00.000",
            duration(null, 20, 1, null) to "20:01.000",
            duration(null, 2, null, null) to "2:00.000",
            duration(null, 2, 1, null) to "2:01.000",
            duration(null, null, 2, 30) to "2.030",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when getting the formatted column, " +
                    "then the result should be \"$expected\"") {
                val obj = DurationColumn("h1", 5)
                val result = obj.formatCell(input)
                println("result = $result")
                assertEquals(expected, result)
            }
        }
}