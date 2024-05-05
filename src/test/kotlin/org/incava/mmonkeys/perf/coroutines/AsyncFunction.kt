package org.incava.mmonkeys.perf.coroutines

import kotlinx.coroutines.*
import org.incava.mmonkeys.trials.perf.rand.Maths
import org.incava.ikdk.io.Console
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

interface IterationDelegate {
    fun iterationMatch(name: String, threadNum: Int, iteration: Int)
}

interface AsyncTrial {
    fun name(): String

    fun announce(threadNum: Int) = println("thread: ${Thread.currentThread().name} $threadNum starting")

    fun iterationMatch(name: String, threadNum: Int, iteration: Int)

    fun count(): Long {
        return -420L
    }
}

interface AsyncFunction: AsyncTrial {
    fun runFunction(threadNum: Int): Thread
}

abstract class AsyncFunctionImpl(val params: CompareParams, private val delegate: IterationDelegate) : AsyncFunction,
    IterationDelegate by delegate {
    private val tick = (params.numIterations * 0.25).toInt()

    fun checkIteration(threadNum: Int, iteration: Int) {
        if (iteration % tick == 0) {
            iterationMatch(name(), threadNum, iteration)
        }
    }
}

open class RepeatNestedFunctionNoCount(params: CompareParams, delegate: IterationDelegate) :
    AsyncFunctionImpl(params, delegate) {
    override fun name() = "f1 : method { repeat { repeat { spin(null) } } }"

    override fun runFunction(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                repeatNested(threadNum, iteration)
                checkIteration(threadNum, iteration)
            }
        }
    }

    private fun repeatNested(i: Int, j: Int) {
        repeat(params.outerCount) {
            repeat(params.innerCount) {
                Maths.spin(null, i, j)
            }
        }
    }
}

open class RepeatNestedFunctionCount(params: CompareParams, delegate: IterationDelegate) :
    AsyncFunctionImpl(params, delegate) {
    private val count = AtomicLong()

    override fun name() = "f5 : method { repeat { repeat { spin(count) } } }"

    override fun runFunction(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                repeatNested(threadNum, iteration)
                checkIteration(threadNum, iteration)
            }
        }
    }

    private fun repeatNested(i: Int, j: Int) {
        repeat(params.outerCount) {
            repeat(params.innerCount) {
                Maths.spin(count, i, j)
            }
        }
    }

    override fun count(): Long {
        return count.get()
    }
}

open class RepeatRepeatFunctionCount(params: CompareParams, delegate: IterationDelegate) :
    AsyncFunctionImpl(params, delegate) {
    private val count = AtomicLong()

    override fun name() = "f6 : repeat { repeat { spin(count) } }"

    override fun runFunction(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                repeat(params.outerCount) {
                    repeat(params.innerCount) {
                        Maths.spin(count, threadNum, iteration)
                    }
                }
                checkIteration(threadNum, iteration)
            }
        }
    }

    override fun count(): Long {
        return count.get()
    }
}

class RepeatMethod(params: CompareParams, delegate: IterationDelegate) : AsyncFunctionImpl(params, delegate) {
    private val count = AtomicLong()

    override fun name() = "f7 : repeat { method { repeat { spin(count) } } }"

    override fun runFunction(threadNum: Int): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                repeat(params.outerCount) {
                    repeatOnce(threadNum, iteration)
                }
                checkIteration(threadNum, iteration)
            }
        }
    }

    private fun repeatOnce(i: Int, j: Int) {
        repeat(params.innerCount) {
            Maths.spin(count, i, j)
        }
    }

    override fun count(): Long {
        return count.get()
    }
}

class CoroutineTrial(val params: CompareParams, private val delegate: IterationDelegate) : AsyncTrial,
    IterationDelegate by delegate {
    private val tick = (params.numIterations * 0.25).toInt()
    val count = AtomicLong()

    override fun count(): Long {
        return count.get()
    }

    override fun name() = "c8 : launch { repeat { repeat { spin(count) } } }"

    fun checkIteration(threadNum: Int, iteration: Int) {
        if (iteration % tick == 0) {
            iterationMatch(name(), threadNum, iteration)
        }
    }

    fun runIt() {
        Console.info("runCoroutines")
        runBlocking {
            withContext(Dispatchers.Default) {
                for (jobNum in 0 until params.numThreads) {
                    launch {
                        // println("launch | in launch: ${Thread.currentThread().name} ($jobNum)")
                        for (iteration in 0 until params.numIterations) {
                            repeat(params.outerCount) {
                                repeat(params.innerCount) {
                                    Maths.spin(count, jobNum, iteration)
                                }
                            }
                            delay(5)
                            checkIteration(jobNum, iteration)
                        }
                        // println("launch | done: ${Thread.currentThread().name} ($jobNum)")
                    }
                    // println("launch | launched: ${Thread.currentThread().name} ($jobNum)")
                }
            }
        }
        // Console.info("count", count)
    }
}