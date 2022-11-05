package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.time.Durations
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console
import kotlin.system.measureTimeMillis

typealias TypewriterCtor = (List<Char>) -> Typewriter
typealias MatcherCtor<T> = (Monkey, T) -> Matcher

class PerfTrial<T>(lastChar: Char, val sought: T, typeCtor: TypewriterCtor, matcherCtor: MatcherCtor<T>) {
    val matcher: Matcher

    init {
        val chars = Keys.keyList(lastChar)
        val typewriter = typeCtor.invoke(chars)
        val monkey = Monkey(38, typewriter)
        matcher = matcherCtor(monkey, sought)
    }

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
        pause()
        val duration = Durations.measureDuration {
            repeat(numMatches) {
                val dur = measureTimeMillis {
                    val result = matcher.run()
                    iterations += result
                }
                durations += dur
            }
        }
        return PerfResults("name-s1", matcher, duration.second, durations, iterations)
    }
}
