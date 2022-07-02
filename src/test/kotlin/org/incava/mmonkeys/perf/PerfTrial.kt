package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.match.StringMatcher
import kotlin.system.measureTimeMillis

typealias TypewriterCtor = (List<Char>) -> StandardTypewriter
typealias MatcherCtor = (Monkey, String) -> StringMatcher

class PerfTrial(lastChar: Char, sought: String, typeCtor: TypewriterCtor, matchCtor: MatcherCtor) {
    val matcher: StringMatcher

    init {
        val chars = ('a'..lastChar).toList() + ' '
        val typewriter = typeCtor.invoke(chars)
        val monkey = Monkey(38, typewriter)
        matcher = matchCtor.invoke(monkey, sought)
    }

    private fun pause() {
        Thread.sleep(100L)
    }

    fun run(numMatches: Int): PerfTestResults {
        val result = runTrials(numMatches)
        pause()
        return result
    }

    private fun runTrials(numMatches: Int): PerfTestResults {
        val durations = mutableListOf<Long>()
        val iterations = mutableListOf<Long>()
        pause()
        val duration = measureTimeMillis {
            repeat(numMatches) {
                durations += measureTimeMillis {
                    val result = matcher.run()
                    iterations += result
                }
            }
        }
        return PerfTestResults("name-s1", matcher, duration, durations, iterations)
    }
}
