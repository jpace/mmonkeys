package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.trials.base.MatcherCtor
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

class CorpusTrialRunner(
    sought: Corpus,
    matcherCtor: MatcherCtor<Corpus>,
    private val timeLimit: Duration,
    typewriter: Typewriter = Typewriter(),
) {
    val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()

    init {
        val monkey = MonkeyFactory { typewriter }.createMonkey()
        val durations = mutableListOf<Long>()
        val duration = Durations.measureDuration {
            val matcher = matcherCtor(monkey, sought)
            durations += measureTimeMillis {
                runMatch(matcher)
            }
        }
        results = PerfResults(duration.second, durations, iterations)
    }

    private fun runMatch(matcher: Matcher) {
        val verbose = true
        while (!matcher.isComplete()) {
            var iteration = 0L
            do {
                ++iteration
                val result = matcher.check()
            } while (!result.isMatch && iteration < maxAttempts)
            if (verbose && iterations.size % 1000 == 0) {
                Console.info("iterations.#: ${iterations.size}")
            }
            iterations += iteration
            val now = ZonedDateTime.now()
            val elapsed = Duration.between(start, now)
            if (elapsed > timeLimit) {
                Console.info("stopping at: $elapsed")
                return
            }
        }
    }
}