package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class StringMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {
}