package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console.log

open class MatchMonkeys(
    private val list: List<Monkey>,
    private val sought: String,
    private val matching: ((monkey: Monkey, sought: String) -> Matcher),
    maxAttempts: Long,
) : BaseMonkeys(maxAttempts) {
    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            val matcher = matching.invoke(monkey, sought)
            runMatcher(matcher)
        }
    }

    private suspend fun runMatcher(matcher: Matcher) {
        (0 until maxAttempts).forEach { iteration ->
            when {
                found.get() -> {
                    return
                }
                matcher.runIteration() -> {
                    iterations.incrementAndGet()
                    log("success", matcher.monkey.id)
                    log("iteration", iteration)
                    log("matcher iter", matcher.iteration)
                    found.set(true)
                    return
                }
                else -> {
                    iterations.incrementAndGet()
                    delay(5L)
                }
            }
        }
    }
}