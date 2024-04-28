package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test

internal class RandoughTest {

    @Test
    fun nextInt() {
        val obj = Randough(4)
        val digits = 3
        val result = obj.nextInt(digits)
        Console.info("result", result)
    }

    @Test
    fun nextLong() {
    }

    @Test
    fun getLength() {
    }
}