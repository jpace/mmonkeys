package org.incava.mmonkeys.perf.rand

import org.incava.mesa.Column
import org.incava.mesa.DoubleColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.util.Console.info
import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class RandTrialTable : Table() {
    override fun columns(): List<Column> {
        return listOf(
            StringColumn("name", 32, true),
            DoubleColumn("average", 8, 1)
        )
    }
}

class RandTrial(val name: String, val block: () -> Unit) {
    val durations: MutableList<Long> = mutableListOf()

    fun average(): Double {
        return durations.average()
    }
}

class RandIntVsCharTest {
    private fun runTrial(count: Int, trial: RandTrial) {
        val blk = trial.block
        val duration = measureTimeMillis {
            repeat(count) {
                blk()
            }
        }
        trial.durations += duration
    }

    fun nextInt() {
        println("nextRand")
        val count = 10_000_000
        val random = Random.Default
        val nextInt = { random.nextInt(); Unit }
        val nextLong = { random.nextLong(); Unit }
        val numCharsInt = 6
        val charSizeInt = 27.0.pow(numCharsInt).toInt()
        val numCharsLong = 9
        val charSizeLong = 27.0.pow(numCharsLong).toLong()
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
            repeat(numCharsInt) {
                val ch = random.nextInt(0, 27)
                sb.append(ch)
            }
        }
        val longIf = {
            repeat(numCharsInt) {
                val ch = random.nextInt(0, 27)
                val b = ch == 26
            }
        }
        val longIfAppend = {
            val sb = StringBuilder()
            repeat(numCharsInt) {
                val ch = random.nextInt(0, 27)
                if (ch != 26)
                    sb.append(ch)
            }
        }
        val trials = listOf(
            RandTrial("nextInt ", nextInt),
            RandTrial("nextInt $numCharsInt", nextIntSize),
            RandTrial("nextLong", nextLong),
            RandTrial("nextLongInt $numCharsInt", nextLongInt),
            RandTrial("nextLongLong $numCharsLong", nextLongLong),
            RandTrial("int * $numCharsInt", intChars),
            RandTrial("intAppend * $numCharsInt", intAppend),
            RandTrial("intIf * $numCharsInt", intIf),
            RandTrial("intIfAppend * $numCharsInt", intIfAppend),
            RandTrial("long * $numCharsLong", longChars),
            RandTrial("longAppend * $numCharsLong", longAppend),
            RandTrial("longIf * $numCharsLong", longIf),
            RandTrial("longIfAppend * $numCharsLong", longIfAppend),
        )
        repeat(10) {
            info("iteration", it)
            val shuffled = trials.shuffled()
            shuffled.forEach { trial ->
                runTrial(count, trial)
            }
        }
        println()
        val table = RandTrialTable()
        table.printHeader()
        trials.forEach { trial ->
            table.printRow(trial.name, trial.durations.average())
        }
    }
}

fun main() {
    val obj = RandIntVsCharTest()
    obj.nextInt()
}