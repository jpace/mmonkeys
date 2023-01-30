package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.MatcherRunner
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import kotlin.system.measureTimeMillis

typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class PerfTrial<T>(val sought: T, val typewriter: Typewriter, val matcherCtor: MatcherCtor<T>) {
    private fun pause() {
        Thread.sleep(100L)
    }

    fun run(numMatches: Int): PerfResults {
        val durations = mutableListOf<Long>()
        val iterations = mutableListOf<Long>()
        val monkey = Monkey(38, typewriter)
        pause()
        val start = System.currentTimeMillis()
        val duration = Durations.measureDuration {
            repeat(numMatches) {
                val matcher = matcherCtor(monkey, sought)
                val matcherRunner = MatcherRunner(matcher)
                val dur = measureTimeMillis {
                    while (!matcherRunner.isComplete()) {
                        val result = matcherRunner.run()
                        iterations += result
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
