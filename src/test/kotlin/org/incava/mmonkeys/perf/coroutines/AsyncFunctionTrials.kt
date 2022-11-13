package org.incava.mmonkeys.perf.coroutines

import org.incava.mesa.*
import org.incava.time.Durations
import org.incava.mmonkeys.util.Console
import java.lang.Thread.sleep
import java.time.Duration

data class TrialResult(val name: String, val duration: Duration, val count: Long)

class AsyncFunctionTrials(val params: CompareParams) {
    val iterationDelegate = object : IterationDelegate {
        override fun iterationMatch(name: String, threadNum: Int, iteration: Int) {
            table.writeRow(name, threadNum, iteration, Thread.currentThread().name)
        }
    }

    private val table = object : Table() {
        override fun columns(): List<Column> {
            return listOf(
                StringColumn("type", 40, leftJustified = true),
                IntColumn("number", 10),
                IntColumn("invocation", 10),
                StringColumn("name", 20, leftJustified = true)
            )
        }

        init {
            writeHeader()
            writeBreak('=')
        }
    }

    fun runThreads(asyncFunction: AsyncFunction): TrialResult {
        val start = System.currentTimeMillis()
        val threads = (0 until params.numThreads).map { asyncFunction.runFunction(it) }
        threads.forEach(Thread::join)
        val done = System.currentTimeMillis()
        val elapsed = done - start
        val duration = Duration.ofMillis(elapsed)
        summarize(duration)
        return TrialResult(asyncFunction.name(), duration, asyncFunction.count())
    }

    fun runCoroutine(trial: CoroutineTrial): TrialResult {
        val start = System.currentTimeMillis()
        trial.runIt()
        val done = System.currentTimeMillis()
        val elapsed = done - start
        val duration = Duration.ofMillis(elapsed)
        summarize(duration)
        return TrialResult(trial.name(), duration, trial.count())
    }

    private fun summarize(duration: Duration) {
        Console.info("duration", Durations.formattedUnits(duration))
    }

    fun summarize() {
        Console.info("threads.#", params.numThreads)
        Console.info("iterations.#", params.numIterations)
        Console.info("outerCount.#", params.outerCount)
        Console.info("innerCount.#", params.innerCount)
        Console.info("total.#", params.outerCount * params.innerCount)
    }

    fun summarize(results: List<TrialResult>) {
//        Console.info("# threads", params.numThreads)
//        Console.info("# iterations", params.numIterations)
        val summaryTable = object : Table() {
            override fun columns(): List<Column> {
                return listOf(
                    StringColumn("type", 60, leftJustified = true),
                    DurationColumn("duration", 10),
                    LongColumn("count", 14)
                )
            }
        }
        summaryTable.writeHeader()
        summaryTable.writeBreak('=')
        results.sortedBy { it.name }.forEach {
            summaryTable.writeRow(it.name, it.duration, it.count)
        }
    }
}

fun main() {
    Console.info("main starting")
    val params = CompareParams(numThreads = 20, numIterations = 10, outerCount = 5_000, innerCount = 5_000)
    val obj = AsyncFunctionTrials(params)
    val functions = listOf(
        RepeatNestedFunctionCount(params, obj.iterationDelegate),
        RepeatNestedFunctionNoCount(params, obj.iterationDelegate),
        RepeatRepeatFunctionCount(params, obj.iterationDelegate),
        RepeatMethod(params, obj.iterationDelegate),
    )
    val results = functions.shuffled().map {
        val result = obj.runThreads(it)
        sleep(1000)
        Console.info("name", it.name())
        Console.info("count", it.count())
        result
    }.toMutableList()
    val trial = CoroutineTrial(params, obj.iterationDelegate)
    val coResults = obj.runCoroutine(trial)
    results += coResults
    Console.info("main done")
    obj.summarize()
    obj.summarize(results)
}