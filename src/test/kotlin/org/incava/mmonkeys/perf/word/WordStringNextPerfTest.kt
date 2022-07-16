package org.incava.mmonkeys.perf.word

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.word.WordMonkey
import java.lang.Thread.sleep
import kotlin.math.pow
import kotlin.system.measureTimeMillis

class WordStringNextPerfTest {
    private val factors = (5..7)
    private val monkey: Monkey = Monkey(37, StandardTypewriter(('a'..'z').toList() + ' '))
    private val wordMonkey = WordMonkey(37, StandardTypewriter(('a'..'z').toList() + ' '))

    private fun runIterations(name: String, iterations: Long, function: () -> Unit) {
        val duration = measureTimeMillis {
            for (i in 0 until iterations) {
                val result = function()
            }
        }
        println("$name: iterations: $iterations; duration: $duration")
        sleep(1000L)
    }

    fun `measure word performance`() =
        factors.map { factor ->
            val iterations = 10.0.pow(factor).toLong()
            runIterations("word", iterations) { wordMonkey.nextWord() }
        }

    fun `measure string performance`() =
        factors.map { factor ->
            val iterations = 10.0.pow(factor).toLong()
            runIterations("string", iterations) { monkey.nextString() }
        }
}

fun main() {
    val obj = WordStringNextPerfTest()
    obj.`measure string performance`()
    obj.`measure string performance`()
}