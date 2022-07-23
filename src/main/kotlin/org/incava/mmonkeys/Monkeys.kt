package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console.log

class Monkeys (
    private val list: List<Monkey>,
    private val sought: String,
    matching: ((monkey: Monkey, sought: String) -> Matcher),
    maxAttempts: Long,
) : MatchMonkeys(list, sought, matching, maxAttempts) {
}