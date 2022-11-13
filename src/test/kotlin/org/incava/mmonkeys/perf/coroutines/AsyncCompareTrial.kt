package org.incava.mmonkeys.perf.coroutines

import org.incava.mesa.*
import org.incava.time.Durations
import org.incava.mmonkeys.util.Console
import java.time.Duration
import kotlin.concurrent.thread

data class CompareTrialParams(val numThreads: Int = 10, val numIterations: Int = 100, val outerCount: Int = 10_000, val innerCount: Int = 10_000)

open class AsyncCompareTrial(val params: CompareTrialParams) {
    private val tick = (params.numIterations * 0.25).toInt()

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

    fun runThreads(meth: (Int) -> Thread): Duration {
        val start = System.currentTimeMillis()
        val threads = (0 until params.numThreads).map(meth)
        threads.forEach(Thread::join)
        val done = System.currentTimeMillis()
        val elapsed = done - start
        val duration = Duration.ofMillis(elapsed)
        summarize(duration)
        return duration
    }

    fun createThread(name: String, threadNum: Int, blk: (Int) -> Unit): Thread {
        return thread {
            announce(threadNum)
            for (iteration in 0 until params.numIterations) {
                println("iteration ($name): $iteration")
                blk(iteration)
                checkIteration(name, threadNum, iteration)
            }
        }
    }

    fun summarize(duration: Duration) {
        Console.info("duration", Durations.formattedUnits(duration))
    }

    fun summarize() {
        Console.info("# threads", params.numThreads)
        Console.info("# iterations", params.numIterations)
        Console.info("# outerCount", params.outerCount)
        Console.info("# innerCount", params.innerCount)
        Console.info("# total", params.outerCount * params.innerCount)
    }

    fun summarize(durations: List<Pair<String, Duration>>) {
        Console.info("# threads", params.numThreads)
        Console.info("# iterations", params.numIterations)
        val summaryTable = object : Table() {
            override fun columns(): List<Column> {
                return listOf(
                    StringColumn("type", 40, leftJustified = true),
                    DurationColumn("duration", 10)
                )
            }
        }
        summaryTable.writeHeader()
        summaryTable.writeBreak('=')
        durations.sortedBy { it.first }.forEach { (type, duration) ->
            summaryTable.writeRow(type, duration)
        }
    }

    fun announce(threadNum: Int) = println("thread: ${Thread.currentThread().name} $threadNum starting")

    fun checkIteration(name: String, threadNum: Int, iteration: Int) {
        if (iteration % tick == 0) {
            table.writeRow(name, threadNum, iteration, Thread.currentThread().name)
        }
    }
}
