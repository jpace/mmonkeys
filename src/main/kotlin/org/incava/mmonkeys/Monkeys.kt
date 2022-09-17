package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.Matcher

class Monkeys(
    private val list: List<Monkey>,
    private val sought: Corpus,
    private val matching: ((Monkey, Corpus) -> Matcher),
) : BaseMonkeys() {
    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            val matcher = matching(monkey, sought)
            runMatcher(matcher)
        }
    }
}