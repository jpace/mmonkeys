package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class StringsMatcher(monkey: Monkey, val sought: Array<String>) : Matcher(monkey) {
}