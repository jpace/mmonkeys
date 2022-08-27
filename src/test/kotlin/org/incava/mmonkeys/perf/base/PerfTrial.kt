package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.StringMatcher
import org.incava.mmonkeys.type.Typewriter
import kotlin.system.measureTimeMillis

typealias TypewriterCtor = (List<Char>) -> Typewriter
typealias MatcherCtor = (Monkey, Corpus) -> StringMatcher

class PerfTrial(lastChar: Char, sought: Corpus, typeCtor: TypewriterCtor, matchCtor: MatcherCtor) {
    val stringMatcher: StringMatcher

    init {
        val chars = ('a'..lastChar).toList() + ' '
        val typewriter = typeCtor.invoke(chars)
        val monkey = Monkey(38, typewriter)
        stringMatcher = matchCtor.invoke(monkey, sought)
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
                    val result = stringMatcher.run()
                    iterations += result
                }
            }
        }
        return PerfResults("name-s1", stringMatcher, duration, durations, iterations)
    }
}
