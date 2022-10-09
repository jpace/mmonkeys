package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.Typewriter
import kotlin.system.measureTimeMillis

typealias TypewriterCtor = (List<Char>) -> Typewriter

class PerfTrial<T>(lastChar: Char, sought: T, typeCtor: TypewriterCtor, matchCtor: (Monkey, T) -> Matcher) {
    val matcher: Matcher

    init {
        val chars = ('a'..lastChar).toList() + ' '
        val typewriter = typeCtor.invoke(chars)
        val monkey = Monkey(38, typewriter)
        matcher = matchCtor(monkey, sought)
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
        val duration = measureTimeMillis {
            repeat(numMatches) {
                durations += measureTimeMillis {
                    val result = matcher.run()
                    iterations += result
                }
            }
        }
        return PerfResults("name-s1", matcher, duration, durations, iterations)
    }
}
