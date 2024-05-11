package org.incava.mmonkeys.trials.perf.rand

import org.incava.ikdk.io.Console.info
import org.incava.mesa.DurationColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.testutil.InvokeTrial
import kotlin.math.pow
import kotlin.random.Random

class RandIntVsCharTrial {
    fun nextInt() {
        println("nextRand")
        val numInvokes = 100_000_000L
        val random = Random.Default
        val nextInt = { random.nextInt(); Unit }
        val nextLong = { random.nextLong(); Unit }
        val numCharsInt = 6
        val charSizeInt = 27.0.pow(numCharsInt).toInt()
        val numCharsLong = 13
        val charSizeLong = 27.0.pow(numCharsLong).toLong()
        println("charSizeLong: $charSizeLong")
        val nextIntSize = { random.nextInt(charSizeInt); Unit }
        val nextLongInt = { random.nextLong(charSizeInt.toLong()); Unit }
        val nextLongLong = { random.nextLong(charSizeLong); Unit }
        val intChars = {
            repeat(numCharsInt) {
                random.nextInt(0, 27)
            }
        }
        val intAppend = {
            val sb = StringBuilder()
            repeat(numCharsInt) {
                val ch = random.nextInt(0, 27)
                sb.append(ch)
            }
        }
        val intIf = {
            repeat(numCharsInt) {
                val ch = random.nextInt(0, 27)
                val b = ch == 26
            }
        }
        val intIfAppend = {
            val sb = StringBuilder()
            repeat(numCharsInt) {
                val ch = random.nextInt(0, 27)
                if (ch != 26)
                    sb.append(ch)
            }
        }
        val longChars = {
            repeat(numCharsLong) {
                random.nextInt(0, 27)
            }
        }
        val longAppend = {
            val sb = StringBuilder()
            repeat(numCharsLong) {
                val ch = random.nextInt(0, 26)
                sb.append(ch)
            }
        }
        val longIf = {
            repeat(numCharsLong) {
                val ch = random.nextInt(0, 27)
                val b = ch == 26
            }
        }
        val longIfAppend = {
            val sb = StringBuilder()
            repeat(numCharsLong) {
                val ch = random.nextInt(0, 27)
                if (ch != 26)
                    sb.append(ch)
            }
        }
        val intValue = {
            // (26 ** 5) << 1
            val x = random.nextInt(0, 617831552)
        }

        val longValue = {
            // (26 ** 13) << 1
            val x = random.nextLong(0, 4962305746407473152L)
        }
        val trials3 = listOf(
            "nextInt " to nextInt,
            "nextInt $numCharsInt" to nextIntSize,
            "nextLong" to nextLong,
            "nextLongInt $numCharsInt" to nextLongInt,
            "nextLongLong $numCharsLong" to nextLongLong,
            "int * $numCharsInt" to intChars,
            "intAppend * $numCharsInt" to intAppend,
            "intIf * $numCharsInt" to intIf,
            "intIfAppend * $numCharsInt" to intIfAppend,
            "long * $numCharsLong" to longChars,
            "longAppend * $numCharsLong" to longAppend,
            "longIf * $numCharsLong" to longIf,
            "longIfAppend * $numCharsLong" to longIfAppend,
            "intValue" to intValue,
            "longValue" to longValue,
            "longValue2" to longValue
        )
        val trials2 = listOf(
            "intAppend * $numCharsInt" to intAppend,
            "longAppend * $numCharsLong" to longAppend,
            "intValue" to intValue,
            "longValue" to longValue,
        )
        val toTest = trials3.map { (name, block) -> name to InvokeTrial(numInvokes, block) }
        val comparison = Comparison(*toTest.toTypedArray())
        comparison.run()
        println()
        val table = Table(
            listOf(
                StringColumn("name", 32, true),
                DurationColumn("avg duration", 8)
            )
        )
        table.writeHeader()
        toTest.forEach { trial ->
            table.writeRow(trial.first, trial.second.durations.average())
        }
    }
}

fun main() {
    val obj = RandIntVsCharTrial()
    obj.nextInt()
}