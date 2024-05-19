package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.system.measureTimeMillis

class CorpusTrialRunner(
    sought: Corpus,
    matcherCtor: (Monkey, Corpus) -> CorpusMatcher,
    private val timeLimit: Duration,
    typewriter: Typewriter = Typewriter(),
) {
    val results: PerfResults
    private val maxAttempts = 100_000_000_000_000L
    private val iterations = mutableListOf<Long>()
    private val start = ZonedDateTime.now()

    init {
        val charSupplier = { Keys.fullList() }
        val matcherSupplier: (Monkey, Corpus) -> CorpusMatcher = { monkey, _ -> matcherCtor(monkey, sought) }
        val monkeyFactory = MonkeyFactory({ _: List<Char> -> typewriter }, charSupplier, matcherSupplier)
        val monkey = monkeyFactory.createMonkey()
        val durations = mutableListOf<Long>()
        val totalDuration = Durations.measureDuration {
            val matcher = matcherCtor(monkey, sought)
            durations += measureTimeMillis {
                runMatch(matcher)
            }
        }
        println("durations: $durations")
        println("totalDuration: $totalDuration")
        results = PerfResults(totalDuration.second, durations, iterations)
    }

    private fun runMatch(matcher: Matcher) {
        val verbose = true
        while (!matcher.isComplete()) {
            var iteration = 0L
            do {
                ++iteration
                val result = matcher.check()
            } while (!result.isMatch && iteration < maxAttempts)
            if (verbose && iterations.isNotEmpty() && iterations.size % 5000 == 0) {
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