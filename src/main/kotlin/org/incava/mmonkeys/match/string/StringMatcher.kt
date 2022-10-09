package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher

abstract class StringMatcher(monkey: Monkey, val sought: String) : Matcher(monkey)