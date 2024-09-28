package org.incava.rando

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BitsStringTest {
    @Test
    fun splitify() {
        val str = "abcd1234wxyz"
        val result = BitsString.splitify(str, '_')
        assertEquals("abcd_1234_wxyz", result)

        val str1 = "abd1234wxyz"
        val result1 = BitsString.splitify(str1, '_')
        assertEquals("abd_1234_wxyz", result1)

        val str2 = "wxyz"
        val result2 = BitsString.splitify(str2, '_')
        assertEquals("wxyz", result2)
    }

    @Test
    fun longToInts() {
        val number =  8_590_897_405_908_228_619L
        val (x, y) = BitsString.longToInts(number)
        assertEquals(2_000_224_172, x)
        assertEquals(352_066_059, y)
    }
}