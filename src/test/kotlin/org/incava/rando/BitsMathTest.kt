package org.incava.rando

import org.incava.ikdk.io.Console
import kotlin.test.Test

class BitsMathTest {
    @Test
    fun testIntOutput() {
        val number =  352_066_059
        val bits = Integer.toBinaryString(number).padStart(Int.SIZE_BITS, '0')
        println("$bits ($number)")
        repeat(4) {
            val chars = bits.substring(bits.length - 7 * (it + 1), bits.length - 7 * it)
            val indent = " ".repeat(bits.length - 7 * (it + 1))
            val value = (number shr (7 * it)) and 0x7f
            val pad = " ".repeat(it * 7)
            println("$indent$chars$pad ($value)")
        }
    }

    @Test
    fun testLongOutput() {
        val number =  8_590_897_405_908_228_619L
        val (x, y) = BitsString.longToInts(number)
        Console.info("x", x)
        Console.info("y", y)
        val xbits = Integer.toBinaryString(x).padStart(Int.SIZE_BITS, '0')
        val ybits = Integer.toBinaryString(y).padStart(Int.SIZE_BITS, '0')
        Console.info("xbits", xbits)
        Console.info("ybits", ybits)
        val bits = xbits + ybits
        println("$bits ($number)")
        repeat(9) {
            val chars = bits.substring(bits.length - 7 * (it + 1), bits.length - 7 * it)
            val indent = " ".repeat(bits.length - 7 * (it + 1))
            val value = (number shr (7 * it)) and 0x7f
            val pad = " ".repeat(it * 7)
            println("$indent$chars$pad ($value)")
        }
    }
}