package org.incava.mmonkeys.perf

import kotlinx.coroutines.*
import org.incava.mmonkeys.util.Console
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.math.sqrt
import kotlin.random.Random

class CoroutinesTest {
    private val numThreads = 5
    private val numIterations = 100
    private val tick = numIterations / 10

    fun churn(type: String, iteration: Int) {
        Console.info("churn iteration", iteration)
        val start = System.currentTimeMillis()
        repeat(1_000_000) {
            repeat(1_000_000) {
                val a = iteration.toDouble() * 10_000
                val b = sqrt(a) * sqrt(iteration.toDouble())
                val c = sqrt(b) * sqrt(numIterations.toDouble())
                val d = sqrt(c) * sqrt(numIterations.toDouble())
//                if (Random.Default.nextInt(1_000_000_000) == 0) {
//                    nothing(type, iteration, d)
//                }
            }
        }
        val done = System.currentTimeMillis()
        val duration = done - start
        Console.info("churn duration", duration)
    }

    fun nothing(type: String, iteration: Int, obj: Any): String {
        Console.info("$type | nothing | iteration", iteration)
        return obj.toString()
    }

    fun testIt(type: String, threadNum: Int, iteration: Int) {
        repeat(10_000_000) {
            repeat(1_000_000) {
                val a = iteration.toDouble() * 10_000
                val b = sqrt(a) * sqrt(iteration.toDouble())
                val c = sqrt(b) * sqrt(numIterations.toDouble())
                val d = sqrt(c) * sqrt(numIterations.toDouble())
            }
        }
        if (iteration % tick == 0) {
            show(type, threadNum, iteration)
        }
    }

    fun runThreads() {
        Console.info("this", this)
        val start = System.currentTimeMillis()

        val threads = (0 until numThreads).map { i ->
            thread {
                println("thread: ${Thread.currentThread().name} $i starting")
                for (j in 0 until numIterations) {
                    churn("thread", j)
//                    repeat(10_000_000) {
//                        repeat(1_000_000) {
//                            val a = j.toDouble() * i
//                            val b = sqrt(a) * sqrt(j.toDouble())
//                            val c = sqrt(b) * sqrt(numIterations.toDouble())
//                            val d = sqrt(c) * sqrt(numIterations.toDouble())
//                        }
//                    }
                    if (j % tick == 0) {
                        show("thread", i, j)
                    }
                }
            }
        }
        threads.forEach(Thread::join)
        val done = System.currentTimeMillis()
        val duration = done - start
        Console.info("thread duration", duration)
    }

    private fun show(type: String, i: Int, j: Number) {
        System.out.printf("%-8s | %3d | %,14d | %s\n", type, i, j, Thread.currentThread().name)
    }

    fun runLaunches() {
        Console.info("runLaunches")
        val start = System.currentTimeMillis()
        runBlocking {
            withContext(Dispatchers.Default) {
                for (i in 1..numThreads) {
                    println("launch: ${Thread.currentThread().name} index $i")
                    val job = launch {
                        println("launch: ${Thread.currentThread().name} $i starting")
                        for (j in 1..numIterations) {
                            churn("coroutine", j)
//                            repeat(10_000_000) {
//                                repeat(1_000_000) {
//                                    val a = j.toDouble() * i
//                                    val b = sqrt(a) * sqrt(j.toDouble())
//                                    val c = sqrt(b) * sqrt(numIterations.toDouble())
//                                    val d = sqrt(c) * sqrt(numIterations.toDouble())
//                                }
//                            }
                            delay(10)
                            if (j % tick == 0) {
                                show("job", i, j)
                            }
                        }
                        println("launch: ${Thread.currentThread()} $i is done")
                    }
                    println("launch: ${Thread.currentThread()} job $job ($i)")
                }
            }
        }
        val done = System.currentTimeMillis()
        val duration = done - start
        Console.info("jobs duration", duration)
    }
}

fun main() {
    Console.info("main starting")
    val obj = CoroutinesTest()
    if (true) {
        obj.runLaunches()
    }
    sleep(100)
    if (true) {
        obj.runThreads()
    }
    Console.info("main done")
}