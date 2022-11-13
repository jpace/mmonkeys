package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher

abstract class CorpusMatcher(monkey: Monkey, val sought: Corpus) : Matcher(monkey)