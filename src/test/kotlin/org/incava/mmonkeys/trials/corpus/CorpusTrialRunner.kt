package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.CorpusMonkeyFactory
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.time.Durations
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

class CorpusTrialRunner<T : Corpus>(
    val sought: T,
    monkeyFactory: CorpusMonkeyFactory<T>,
    private val timeLimit: Duration,
    private val tickSize: Int = 20000,
) {
    val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private val matches = mutableListOf<MatchData>()
    private var verbose = false

    init {
        sought.summarize()
        val durations = mutableListOf<Long>()
        val totalDuration = Durations.measureDuration {
            val monkey = monkeyFactory.createMonkey(sought)
            Console.info("monkey.class", monkey.javaClass)
            durations += measureTimeMillis {
                runMonkey(monkey)
                monkey.matchKeystrokes.toSortedMap().forEach { (strokes, count) ->
                    Console.info("strokes", strokes)
                    Console.info("count", count)
                }
            }
        }
        results = PerfResults(sought, totalDuration.second, durations, iterations, matches)
    }

    private fun runMonkey(monkey: Monkey) {
        Console.info("matcher", monkey.javaClass.name)
        while (sought.hasUnmatched()) {
            var iteration = 0L
            var result: MatchData
            do {
                ++iteration
                result = monkey.check()

            } while (!result.isMatch && iteration < maxAttempts && !sought.isEmpty())
            if (verbose) {
                Console.info("result.match?", result.isMatch)
                Console.info("sought.matched.#", sought.matched.size)
                Console.info("sought.words.#", sought.words.size)
                Console.info("sought.empty?", sought.isEmpty())
                monkey.attempts.summarize()
                if (result.isMatch) {
                    Console.info("monkey.class", monkey.javaClass)
                    Console.info("result.keystrokes", result.keystrokes)
                    Console.info("result.index", result.index)
                    Console.info("word", sought.words[result.index])
                }
            }
            if (result.isMatch) {
                matches += result
            }
            iterations += iteration
            if (iterations.size % tickSize == 0) {
                Console.info("iterations.#", iterations.size)
            }
            val now = ZonedDateTime.now()
            val elapsed = Duration.between(start, now)
            if (elapsed > timeLimit) {
                Console.info("stopping", elapsed)
                return
            }
        }
    }
}