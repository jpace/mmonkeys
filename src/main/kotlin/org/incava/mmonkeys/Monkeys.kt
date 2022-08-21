package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console

class Monkeys(
    private val list: List<Monkey>,
    private val sought: String,
    private val matching: ((Monkey, String) -> Matcher),
    maxAttempts: Long,
) : BaseMonkeys(maxAttempts) {
    private val whence = "Monkeys"

    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            val matcher = matching.invoke(monkey, sought)
            runMatcher(matcher)
        }
    }

    private suspend fun runMatcher(matcher: Matcher) {
        (0 until maxAttempts).forEach { iteration ->
            if (found.get()) {
                return
            } else {
                val md = matcher.runIteration()
                if (md.isMatch) {
                    iterations.incrementAndGet()
                    Console.info(whence, "success", matcher.monkey.id)
                    Console.info(whence, "iteration", iteration)
                    Console.info(whence, "iterations", iterations)
                    found.set(true)
                    return
                } else {
                    iterations.incrementAndGet()
                    delay(5L)
                }
            }
        }
    }
}