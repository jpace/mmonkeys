package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matching
import org.incava.mmonkeys.match.corpus.Corpus
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

open class CoroutineCorpusSimulation(
    private val sought: Corpus,
    private val matchCtor: (Monkey, Corpus) -> Matching,
    monkeys: List<Monkey>
) : CoroutineSimulation(monkeys) {
    private val maxAttempts = 100_000_000L

    override fun CoroutineScope.launchMonkeys(): List<Job> {
        return monkeys.map { monkey ->
            launch {
                val matcher = matchCtor(monkey, sought)
                runMatcher(matcher)
            }
        }
    }

    override suspend fun runMatcher(matcher: Matching) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMatcher(matcher, attempt)) {
                return
            }
        }
        Console.info("match failed", this)
    }

    override suspend fun checkMatcher(matcher: Matching, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = matcher.check()
        if (md.isMatch) {
            //$$$ todo - fix this so it doesn't stop at the *first* match (which assumed string, not corpus)
            if (verbose) {
                Console.info("md.match", md)
                Console.info("matcher.to_s", matcher)
                Console.info("attempt", attempt)
                Console.info("iterations", iterations.get())
            }
            found.set(true)
            return true
        } else {
            delay(5L)
        }
        return false
    }
}