package org.incava.mmonkeys.trials.perf.base

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class PerfTrial<T>(private val sought: T, val typewriter: Typewriter, matcherCtor: MatcherCtor<T>, private val numMatches: Int = 1) {
    val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()
    private val timeLimit = Duration.ofMinutes(10L)

    init {
        Console.info("this is <init>")
        val monkey = Monkey(38, typewriter)
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
        while (!matcher.isComplete()) {
            var iteration = 0L
            do {
                ++iteration
                val result = matcher.check()
            } while (!result.isMatch && iteration < maxAttempts)
            Console.info("iteration: $iteration")
            Console.info("")
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
