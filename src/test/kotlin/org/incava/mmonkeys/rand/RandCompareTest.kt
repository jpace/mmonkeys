package org.incava.mmonkeys.rand

import org.incava.mmonkeys.util.Console.log
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.random.Random

@Disabled
internal class RandCompareTest : RandTest() {
    private fun generatedTest(numTrials: Int) {
        val obj = RandGenerated()
        runTimer("generated") {
            obj.generate(27, numTrials)
        }
    }

    private fun calculatedTest(numTrials: Int) {
        val slotSize = numTrials / 100
        val obj = RandCalculated()
        runTimer("calculated") {
            val result = obj.calculate(27, numTrials)
            obj.reduceSlots(result, slotSize)
        }
    }

    @TestFactory
    fun compare() =
        inputs.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when running the generate method, " +
                    "then the result should be within distance $expected") {
                val b = Random.Default.nextBoolean()
                if (b) {
                    generatedTest(input)
                    calculatedTest(input)
                } else {
                    calculatedTest(input)
                    generatedTest(input)
                }
            }
        }
}