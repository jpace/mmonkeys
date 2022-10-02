package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Matcher

class Monkeys<T>(
    private val list: List<Monkey>,
    private val sought: T,
    private val matching: (Monkey, T) -> Matcher,
    showMemory: Boolean,
) : BaseMonkeys(showMemory) {
    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            val matcher = matching(monkey, sought)
            runMatcher(matcher)
        }
    }
}
