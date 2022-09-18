package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class CorpusMatcher(monkey: Monkey, val sought: Corpus) : Matcher(monkey) {
}