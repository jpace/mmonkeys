package org.incava.mmonkeys.rand

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class RandGeneratedTest : RandTest() {
    @TestFactory
    fun generateTest() =
        inputs.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when running the generate method, " +
                    "then the result should be within distance $expected") {
                val obj = RandGenerated()
                val results = obj.generate(27, input)
                val result98 = results.getOrDefault(98, 0.0)
                assertWithin(exp98, result98, expected)
                val result99 = results.getOrDefault(99, 0.0)
                assertWithin(exp99, result99, expected)
            }
        }
}