package org.incava.rando

import org.incava.ikdk.io.Console
import kotlin.test.Test

class BitsMathTest {
    fun bitsToString(number: Int): String {
        return Integer.toBinaryString(number).padStart(Int.SIZE_BITS, '0')
    }

    fun showBitsLine(leftSpaces: Int, chars: String, rightSpaces: Int, value: Int) {
        val leftPad = " ".repeat(leftSpaces)
        val rightPad = " ".repeat(rightSpaces)
        println("$leftPad$chars$rightPad ($value) !")
    }

    @Test
    fun testIntOutput() {
        val number = 352_066_059
        val bits = bitsToString(number)
        val num = String.format("%,d", number)
        println("$bits ($num) ?")
        repeat(4) {
            val leftSpaces = bits.length - 7 * (it + 1)
            val rightSpaces = it * 7
            val chars = extractBits(bits, it)
            val value = (number shr (7 * it)) and 0x7f
            showBitsLine(leftSpaces, chars, rightSpaces, value)
            var v = value
            repeat(6) {
                v = v shr 1 or (v and 0x01 shl 6)
                val newValueStr = bitsToString(v)
                val newValueBits = extractBits(newValueStr, 0)
                showBitsLine(leftSpaces, newValueBits, rightSpaces, v)
            }
            println()
        }
    }

    fun summarize(number: Int, offset: Int) {
        val bits = bitsToString(number)
        val leftSpaces = bits.length - 7 * (offset + 1)
        val rightSpaces = offset * 7
        val chars = extractBits(bits, offset)
        val value = (number shr (7 * offset)) and 0x7f
        showBitsLine(leftSpaces, chars, rightSpaces, value)
    }

    fun writeBitsLine(number: Int) {
        val bits = bitsToString(number)
        val num = String.format("%,d", number)
        println("$bits ($num) ?")
    }

    fun writeBitsLine2(number: Int) {
        val bits = bitsToString(number).replace(Regex("(\\d\\d\\d\\d)(?=\\d)"), "$1-")
        val num = String.format("%,d", number)
        println("$bits ($num) ?")
    }

    @Test
    fun testIntOutput2() {
        val number = 352_066_059
        writeBitsLine(number)
        writeBitsLine2(number)
        repeat(4) {
            summarize(number, it)
        }
        (1 .. 6).forEach { x ->
            Console.info("x", x)
            val shifted = number shr x or (number shl Int.SIZE_BITS - x)
            writeBitsLine2(shifted)
//            writeBitsLine2(number)
//            writeBitsLine2(number shr x)
//            writeBitsLine2(number ushr x)
//            writeBitsLine2(number shl Int.SIZE_BITS - x)
            repeat(4) {
                summarize(shifted, it)
            }
        }
    }

    @Test
    fun testLongOutput() {
        val number = 8_590_897_405_908_228_619L
        val (x, y) = BitsString.longToInts(number)
        Console.info("x", x)
        Console.info("y", y)
        val xbits = Integer.toBinaryString(x).padStart(Int.SIZE_BITS, '0')
        val ybits = Integer.toBinaryString(y).padStart(Int.SIZE_BITS, '0')
        val bits = xbits + ybits
        val num = String.format("%,d", number)
        println("$bits ($num)")
        repeat(9) {
            val chars = bits.substring(bits.length - 7 * (it + 1), bits.length - 7 * it)
            val indent = " ".repeat(bits.length - 7 * (it + 1))
            val value = (number shr (7 * it)) and 0x7f
            val pad = " ".repeat(it * 7)
            println("$indent$chars$pad ($value)")
        }
    }

    @Test
    fun testIntOutputShift() {
        val number = 352_066_059
        val bits = Integer.toBinaryString(number).padStart(Int.SIZE_BITS, '0')
        val num = String.format("%,d", number)
        println("$bits ($num)")
        repeat(4) {
            val chars = bits.substring(bits.length - 7 * (it + 1), bits.length - 7 * it)
            val indent = " ".repeat(bits.length - 7 * (it + 1))
            val value = (number shr (7 * it)) and 0x7f
            val pad = " ".repeat(it * 7)
            println("$indent$chars$pad ($value)")
        }
    }

    fun extractBits(bits: String, offset: Int): String {
        return bits.substring(bits.length - 7 * (offset + 1), bits.length - 7 * offset)
    }
}