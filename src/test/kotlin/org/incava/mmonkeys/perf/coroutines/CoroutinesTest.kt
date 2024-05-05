package org.incava.mmonkeys.perf.coroutines

import kotlinx.coroutines.*
import org.incava.mmonkeys.trials.perf.rand.Maths
import org.incava.ikdk.io.Console
import java.lang.Thread.sleep
import java.time.Duration
import kotlin.concurrent.thread

class CoroutinesTest(params: CompareParams) : AsyncCompare(params) {
    fun createRepeatRepeatThreadBlock(threadNum: Int): Thread {
        return createThread("repeat/repeat/block", threadNum) { iteration ->
            repeat(params.outerCount) { outer ->
                repeat(params.innerCount) { inner ->
                    Maths.spin(outer, inner)
                }
            }
            checkIteration("m 1", threadNum, iteration)
        }
    }

    fun createRepeatRepeatThread(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                repeat(params.outerCount) { outer ->
                    repeat(params.innerCount) { inner ->
                        Maths.spin(outer, inner)
                    }
                }
                checkIteration("th-outer", threadNum, iteration)
            }
        }
    }

    fun createRepeatNestedBlock(threadNum: Int): Thread {
        return createThread("repeat/nested", threadNum) { iteration ->
            repeatNested(threadNum, iteration)
        }
    }

    fun createLoopBlock(threadNum: Int): Thread {
        return createThread("loop", threadNum) { iteration ->
            repeatLoop(threadNum, iteration)
        }
    }

    fun createRepeatNested(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                repeatNested(threadNum, iteration)
                checkIteration("nested", threadNum, iteration)
            }
        }
    }

    fun createInnerChurnThreadBlock(threadNum: Int): Thread {
        return createThread("repeat { method } (blk)", threadNum) { iteration ->
            repeat(params.outerCount) {
                if (it % 1000 == 0) {
                    println("repeat { method } (blk): $it of ${params.outerCount}")
                }
                repeatOnce(threadNum, it)
            }
            checkIteration("repeat { method } (blk)", threadNum, iteration)
        }
    }

    fun createInnerChurnThread(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                println("repeat { method }: iteration $iteration")
                repeat(params.outerCount) {
                    if (it % 1000 == 0) {
                        println("repeat { method }: $it of ${params.outerCount}")
                    }
                    repeatOnce(threadNum, it)
                }
                checkIteration("repeat { method }", threadNum, iteration)
            }
        }
    }

    fun runCoroutines(): Duration {
        Console.info("runCoroutines")
        val start = System.currentTimeMillis()
        runBlocking {
            withContext(Dispatchers.Default) {
                for (jobNum in 1..params.numThreads) {
                    println("launch | launching ${Thread.currentThread().name}  ($jobNum)")
                    launch {
                        println("launch | in launch: ${Thread.currentThread().name} ($jobNum)")
                        for (iteration in 1..params.numIterations) {
//                            churn(jobNum, iteration)
                            repeat(params.outerCount) {
                                repeat(params.innerCount) {
                                    Maths.spin(jobNum, iteration)
                                }
                            }
                            delay(5)
                            checkIteration("job", jobNum, iteration)
                        }
                        println("launch | done: ${Thread.currentThread().name} ($jobNum)")
                    }
                    println("launch | launched: ${Thread.currentThread().name} ($jobNum)")
                }
            }
        }
        val done = System.currentTimeMillis()
        val elapsed = done - start
        val duration = Duration.ofMillis(elapsed)
        summarize(duration)
        return duration
    }

    private fun repeatLoop(i: Int, j: Int) {
        val iters = i.toLong() * j.toLong()
        for (it in 0 until iters) {
            Maths.spin(i, j)
        }
    }

    private fun repeatNested(i: Int, j: Int) {
        repeat(params.outerCount) {
            repeat(params.innerCount) {
                Maths.spin(i, j)
            }
        }
    }

    private fun repeatOnce(i: Int, j: Int) {
        repeat(params.innerCount) {
            Maths.spin(i, j)
        }
    }
}

fun main() {
    Console.info("main starting")
    val params = CompareParams(numThreads = 10, numIterations = 10, outerCount = 5_000, innerCount = 1_000)
    val obj = CoroutinesTest(params)
    val methods = mapOf(
        "loop { repeat }" to obj::createLoopBlock,
        "method { repeat { repeat } }" to obj::createRepeatNested,
        "method { repeat { repeat } } (blk)" to obj::createRepeatNestedBlock,
        "repeat { method }" to obj::createInnerChurnThread,
        "repeat { method } (blk)" to obj::createInnerChurnThreadBlock,
        "repeat { repeat }" to obj::createRepeatRepeatThread,
        "repeat { repeat } (blk)" to obj::createRepeatRepeatThreadBlock,
    )
    val durations = methods.entries.shuffled().map { entry ->
        println("key: ${entry.key}")
        val result = obj.runThreads(entry.value)
        sleep(1000)
        entry.key to result
    }

    Console.info("main done")
    obj.summarize()
    obj.summarize(durations)
}