package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringMatcher
import org.incava.mmonkeys.util.Console

class Monkeys(
    private val list: List<Monkey>,
    private val sought: Corpus,
    private val matching: ((Monkey, Corpus) -> Matcher),
    maxAttempts: Long = 100_000_000L,
) : BaseMonkeys(maxAttempts) {
    private val whence = "Monkeys"

    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            val matcher = matching(monkey, sought)
            runMatcher(matcher)
        }
    }

    private suspend fun runMatcher(matcher: Matcher) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMatcher(matcher, attempt)) {
                return
            }
        }
    }

    private suspend fun checkMatcher(matcher: Matcher, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = matcher.check()
        if (md.isMatch) {
            Console.info(whence, "success", matcher.monkey.id)
            Console.info(whence, "attempt", attempt)
            Console.info(whence, "iterations", iterations)
            found.set(true)
            return true
        } else {
            delay(5L)
        }
        return false
    }
}