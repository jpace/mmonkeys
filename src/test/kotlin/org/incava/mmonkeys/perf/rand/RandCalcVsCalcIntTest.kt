package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.rand.RandCalculated
import org.incava.mmonkeys.rand.RandIntCalculated
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class CompOption(val name: String, val block: () -> Any) {
    val times = mutableListOf<Long>()

    fun run(count: Int) {
        val duration = measureTimeMillis {
            repeat(count) {
                block()
            }
        }
        times += duration
    }
}

class Comparison(vararg val options: CompOption) {
    private val random = Random.Default

    fun run(count: Int) {
        repeat(10) {
            val offset = random.nextInt(options.size)
            println("offset = ${offset}")
            options.indices.forEach {
                val idx = it % offset
                println("idx   = ${idx}")
                val x = options[idx]
                println("x     = ${x.name}")
                x.run(count)
            }
//            a.run(count)
//            b.run(count)
        }
    }

    fun summarize() {
        options.forEach {
            println("${it.name} : ${it.times.average()}")
        }
    }
}

class RandCalcVsCalcIntTest {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val xc = RandCalculated(size, 10000)
        val yc = RandIntCalculated(size, 10000)
        val count = 10_000_000
        val xt = CompOption("int.map") { yc.nextMapInt() }
        val yt = CompOption("int.int") { yc.nextInt() }
        val comp = Comparison(xt, yt, xt, yt)
        comp.run(count)
        comp.summarize()
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTest()
    obj.nextRand()
}