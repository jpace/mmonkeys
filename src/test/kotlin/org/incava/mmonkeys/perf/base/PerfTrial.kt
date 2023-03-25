package org.incava.mmonkeys.perf.base

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
                        while (iteration < maxAttempts) {
                            val result = matcher.check()
                            if (result.isMatch) {
                                break
                            }
                            ++iteration
                        }
                        iterations += iteration
                        val now = System.currentTimeMillis()
                        if (now - start > 1000L * 60 * 1) {
                            break
                        }
                    }
                }
                durations += dur
            }
        }
        return PerfResults(duration.second, durations, iterations)
    }
}
