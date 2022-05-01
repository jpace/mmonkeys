package org.incava.mmonkeys

import org.incava.mmonkeys.Console.log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.Thread.sleep
import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

internal class WordTest {
    private lateinit var emptyWord: Word
    private lateinit var oneCharWord: Word

    @BeforeEach
    fun setUp() {
        emptyWord = Word()
        oneCharWord = Word()
        oneCharWord += 'a'
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getCharacters() {
        assertTrue(emptyWord.characters.isEmpty())
    }

    @Test
    fun setCharacters() {
        val word = Word()
        word.characters = mutableListOf('a', 'b', 'c')
        assertEquals(listOf('a', 'b', 'c'), word.characters)
    }

    @Test
    fun plusAssign() {
        val word = Word()
        assertEquals(emptyList<Char>(), word.characters)
        word.plusAssign('a')
        assertEquals(listOf('a'), word.characters)
    }

    @Test
    fun `operator +=`() {
        val word = Word()
        assertEquals(emptyList<Char>(), word.characters)
        word += 'a'
        assertEquals(listOf('a'), word.characters)
    }

    @ParameterizedTest
    @MethodSource("dataForLength")
    fun length(expected: Int, obj: Word) {
        val result = obj.length()
        assertEquals(expected, result)
    }

    fun runPerfTest(x: Any, y: Any, z: Any, type: String, iterations: Long) {
        val duration = measureTimeMillis {
            for (i in 0..iterations) {
                val a = x == x
                val b = x == y
                val c = x == z
            }
        }
        println("$type: iterations: $iterations; duration: $duration")
    }

    fun runPerfTestWord(s: String, t: String, iterations: Long) {
        val x = Word(s)
        val y = Word(s)
        val z = Word(t)
        runPerfTest(x, y, z, "Word", iterations)
    }

    fun runPerfTestString(s: String, t: String, iterations: Long) {
        val x = String(s.toByteArray())
        val y = String(s.toByteArray())
        val z = String(t.toByteArray())
        runPerfTest(x, y, z, "String", iterations)
    }

    fun runPerfTest(s: String, t: String, iterations: Long) {
        sleep(2000L)
        val b = Random.nextBoolean()
        log("s", s)
        log("t", t)
        if (b) {
            runPerfTestWord(s, t, iterations)
            sleep(2000L)
            runPerfTestString(s, t, iterations)
        } else {
            runPerfTestString(s, t, iterations)
            sleep(2000L)
            runPerfTestWord(s, t, iterations)
        }
        sleep(2000L)
    }

    @Test
    fun equalsPerformance() {
        val exponent = 8
        val iterations = 10.0.pow(exponent).toLong()
        runPerfTest("this is a test", "this is also a test", iterations)
        runPerfTest("abc", "this is also a test", iterations)
        runPerfTest("abc", "defghi", iterations)
        runPerfTest("abc", "abcdef", iterations)
    }

    companion object {
        @JvmStatic
        fun dataForLength(): List<Arguments> {
            val emptyWord = Word()
            val oneCharWord: Word = Word('a')
            return listOf(
                Arguments.of(0, emptyWord),
                Arguments.of(1, oneCharWord)
            );
        }
    }

    @Test
    fun testToString() {
    }

    @Test
    fun testEquals() {
        val x = Word("abc")
        val y = Word("abc")
        val result = x == y
        assertTrue(result, "x: $x; y: $y")
    }

    @Test
    fun testHashCode() {
    }
}