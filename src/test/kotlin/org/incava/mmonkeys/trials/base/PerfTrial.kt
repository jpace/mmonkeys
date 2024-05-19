package org.incava.mmonkeys.trials.base

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class PerfTrial<T>(
    private val sought: T,
    val typewriter: Typewriter,
    matcherCtor: MatcherCtor<T>,
    private val timeLimit: Duration,
    private val numMatches: Int = 1,
) {
    private val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()

    init {
        val monkey = MonkeyFactory({ typewriter }).createMonkey(id = 38)
        val durations = mutableListOf<Long>()
        val duration = Durations.measureDuration {
            repeat(numMatches) {
                val matcher = matcherCtor(monkey, sought)
                durations += measureTimeMillis {
                    runMatch(matcher)
                }
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
            if (verbose) {
                // Console.info("iteration: $iteration")
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
