package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher

abstract class StringsMatcher(monkey: Monkey, sought: List<String>) : Matcher(monkey) {
    val sought = sought.toMutableList()

    override fun isComplete(): Boolean {
        return sought.isEmpty()
    }
}