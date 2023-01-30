package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertEquals

internal class MathsTest {
    @Test
    fun power1() {
        val expected = 308915776
        val result = Maths.power(26, 6)
        assertEquals(expected, result)
    }

    @Test
    fun power2Int() {
        val expected = 308915776
        val result = Maths.power2(26, 6)
        assertEquals(expected, result)
    }

    @Test
    fun power2Long() {
        val expected = 308915776
        val result = Maths.power2(26, 6)
        assertEquals(expected, result)
    }

    @Test
    fun power2BigInteger() {
        val expected = BigInteger.valueOf(308915776)
        val result = Maths.power2(BigInteger.valueOf(26), 6)
        assertEquals(expected, result)
    }

    @Test
    fun power3() {
        val expected = 308915776
        val result = Maths.power3(26, 6)
        assertEquals(expected, result)
    }

    @Test
    fun power4() {
        val expected = 308915776
        val result = Maths.power4(26, 6)
        assertEquals(expected, result)
    }
}
