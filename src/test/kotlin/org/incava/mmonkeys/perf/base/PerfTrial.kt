package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.time.Durations
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console
import kotlin.system.measureTimeMillis

typealias TypewriterCtor = (List<Char>) -> Typewriter
typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class PerfTrial<T>(private val lastChar: Char, val sought: T, private val typeCtor: TypewriterCtor, val matcherCtor: MatcherCtor<T>) {
    private fun pause() {
        Thread.sleep(100L)
    }

    fun run(numMatches: Int): PerfResults {
        val result = runTrials(numMatches)
        pause()
        return result
    }

    private fun runTrials(numMatches: Int): PerfResults {
        val durations = mutableListOf<Long>()
        val iterations = mutableListOf<Long>()
        val chars = Keys.keyList(lastChar)
        val typewriter = typeCtor.invoke(chars)
        val monkey = Monkey(38, typewriter)
        pause()
        val start = System.currentTimeMillis()
        val duration = Durations.measureDuration {
            repeat(numMatches) {
                val matcher = matcherCtor(monkey, sought)
                val dur = measureTimeMillis {
                    while (!matcher.isComplete()) {
                        val result = matcher.run()
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
