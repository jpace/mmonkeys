package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.Matching
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.time.Durations
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

class CorpusTrialRunner(
    val sought: Corpus,
    monkeyFactory: MonkeyFactory,
    private val timeLimit: Duration,
    private val tickSize: Int = 20000,
) {
    val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private val matches = mutableListOf<MatchData>()
    var verbose = false

    init {
        val monkey = monkeyFactory.createMonkey()
        val durations = mutableListOf<Long>()
        val totalDuration = Durations.measureDuration {
            val matcher = monkeyFactory.corpusMatcher(monkey, sought)
            durations += measureTimeMillis {
                runMatch(matcher)
            }
        }
        results = PerfResults(totalDuration.second, durations, iterations, matches)
    }

    private fun runMatch(matcher: Matching) {
        Console.info("matcher", matcher.javaClass.name)
        while (sought.hasUnmatched()) {
            var iteration = 0L
            var result: MatchData
            do {
                ++iteration
                result = matcher.check()
            } while (!result.isMatch && iteration < maxAttempts)
            if (verbose) {
                Console.info("result.match?", result.isMatch)
                if (result.isMatch) {
                    Console.info("matcher.class", matcher.javaClass)
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