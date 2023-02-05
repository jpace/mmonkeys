package org.incava.mmonkeys.perf.rand

import org.incava.mesa.Column
import org.incava.mesa.DurationColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.testutil.InvokeTrials
import org.incava.ikdk.io.Console.info
import kotlin.math.pow
import kotlin.random.Random

class RandTrialTable : Table() {
    override fun columns(): List<Column> {
        return listOf(
            StringColumn("name", 32, true),
            DurationColumn("average", 8)
        )
    }
}

class RandIntVsCharTrial {
    private fun invokeTrials(name: String, block: () -> Unit): Pair<String, InvokeTrials<Any>> {
        return Pair(name, InvokeTrials(block))
    }

    fun nextInt() {
        println("nextRand")
        val count = 10_000_000L
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
        val trials = listOf(
            invokeTrials("nextInt ", nextInt),
            invokeTrials("nextInt $numCharsInt", nextIntSize),
            invokeTrials("nextLong", nextLong),
            invokeTrials("nextLongInt $numCharsInt", nextLongInt),
            invokeTrials("nextLongLong $numCharsLong", nextLongLong),
            invokeTrials("int * $numCharsInt", intChars),
            invokeTrials("intAppend * $numCharsInt", intAppend),
            invokeTrials("intIf * $numCharsInt", intIf),
            invokeTrials("intIfAppend * $numCharsInt", intIfAppend),
            invokeTrials("long * $numCharsLong", longChars),
            invokeTrials("longAppend * $numCharsLong", longAppend),
            invokeTrials("longIf * $numCharsLong", longIf),
            invokeTrials("longIfAppend * $numCharsLong", longIfAppend),
            invokeTrials("intValue", intValue),
            invokeTrials("longValue", longValue),
        )
        val trials2 = listOf(
            invokeTrials("intAppend * $numCharsInt", intAppend),
            invokeTrials("longAppend * $numCharsLong", longAppend),
            invokeTrials("intValue", intValue),
            invokeTrials("longValue", longValue),
        )
        repeat(10) {
            info("iteration", it)
            val shuffled = trials2.shuffled()
            shuffled.forEach { trial ->
                trial.second.run(count)
            }
        }
        println()
        val table = RandTrialTable()
        table.writeHeader()
        trials2.forEach { trial ->
            table.writeRow(trial.first, trial.second.durations.average())
        }
    }
}

fun main() {
    val obj = RandIntVsCharTrial()
    obj.nextInt()
}