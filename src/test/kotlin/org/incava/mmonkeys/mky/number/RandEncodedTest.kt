package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class RandEncodedTest {
    @Test
    fun random() {
        val result = RandEncoded.random(3)
        Console.info("result", result)
        assertTrue(result >= 702, "result: $result")
        assertTrue(result < 18_278, "result: $result")
        Console.info("result", StringEncoder.decode(result))
    }
}