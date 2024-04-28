package org.incava.mmonkeys.perf.base

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import kotlin.system.measureTimeMillis

typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class PerfTrial<T>(val sought: T, val typewriter: Typewriter, val matcherCtor: MatcherCtor<T>) {
    private val maxAttempts = 100_000_000_000_000L

    fun run(numMatches: Int): PerfResults {
        val durations = mutableListOf<Long>()
        val iterations = mutableListOf<Long>()
        val monkey = Monkey(38, typewriter)
        val start = System.currentTimeMillis()
        val duration = Durations.measureDuration {
            repeat(numMatches) {
                val matcher = matcherCtor(monkey, sought)
                val dur = measureTimeMillis {
                    while (!matcher.isComplete()) {
                        var iteration = 0L
                        do {
                            ++iteration
                            val result = matcher.check()
                        } while (!result.isMatch && iteration < maxAttempts)
                        Console.info("iteration: $iteration")
                        Console.info("")
                        iterations += iteration
                        val now = System.currentTimeMillis()
                        if (now - start > 1000L * 60 * 4) {
                            Console.info("stopping at: ${now - start}")
                            break
                        }
                    }
                }
                durations += dur
            }
        }
        return PerfResults(duration.second, durations, iterations)
    }

    private fun runCheck(matcher: Matcher) {

    }
}
