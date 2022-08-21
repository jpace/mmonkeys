package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test

internal class ConsoleTest {
    @Test
    fun printf() {
        Console.info("ConsoleTest", "testing")
    }
}