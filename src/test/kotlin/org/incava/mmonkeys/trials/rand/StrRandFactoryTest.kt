package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.type.Chars
import kotlin.test.Test

internal class StrRandFactoryTest {
    @Test
    fun assembleString() {
        repeat(100) {
            Console.info("it", it)
            val provided = mutableListOf<Int>()
            val result = StrRandFactory.assemble(8) {
                val num = Chars.randCharAz()
                provided += num
                num
            }
            Console.info("assemble $it - result")
            Console.info("assemble $it - provided", provided)
            Console.info("assemble $it - result", result)
            Console.info("assemble $it - result.class", result.javaClass)
            Console.info("assemble $it - result.#", result.length)
            Console.info("assemble $it - result (2)")
        }
    }

    @Test
    fun buildString() {
        val result = StrRandFactory.build(8) { Chars.randCharAz() }
        Console.info("build - result")
        Console.info("build - result", result)
        Console.info("build - result.#", result.length)
        Console.info("build - result (2)")
    }

    fun assembleString(length: Int, charProvider: () -> Int): String {
        val bytes = ByteArray(length)
        repeat(length) { index ->
            val n = charProvider()
            bytes[index] = ('a' + n).toByte()
        }
        return String(bytes)
    }

    @Test
    fun assembles() {
        val numbers = mutableListOf(1, 0, 2, 3, 11, 15, 11, 17)
        val provider: () -> Int = { numbers.removeAt(0) }
        val result = assembleString(8, provider)
        Console.info("assemble - result")
        Console.info("assemble - result", result)
    }
}
