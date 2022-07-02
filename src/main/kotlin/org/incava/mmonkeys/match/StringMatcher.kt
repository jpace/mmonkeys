package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

open abstract class StringMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {
}