package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated
import kotlin.random.Random

data class Record(val name: String, val counts: MutableMap<Int, Int> = mutableMapOf())

class RandoTrial {
    fun runTest() {
        val x = RandCalculated(27, 100000)
        val y = RandGenerated(27, 10000)
        val z = Random.Default

        val records = listOf(
            Record("calculated"),
            Record("generated"),
            Record("random")
        )

        (0 until 10_000).forEach { _ ->
            val a = x.nextRand()
            addToRecord(records[0], a)
            val b = y.nextRand()
            addToRecord(records[1], b.toInt())
            var c = 1
            while (z.nextInt(27) != 26) {
                c++
            }
            addToRecord(records[2], c)
        }
        records.forEach { printRecord(it) }
        println()
    }

    fun printRecord(record: Record) {
        Console.info("record", record.name)
        Console.info("values", record.counts.toSortedMap())
    }

    fun addToRecord(record: Record, value: Int) {
        record.counts.merge(value, 1) { prev, _ -> prev + 1 }
    }
}

fun main() {
    val obj = RandoTrial()
    obj.runTest()
}