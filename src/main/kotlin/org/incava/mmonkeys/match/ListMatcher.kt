package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class ListMatcher(monkey: Monkey, sought: Array<String>) : Matcher(monkey, sought.first()) {
}