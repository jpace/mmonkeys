package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.Typewriter

abstract class CorpusMonkey(open val corpus: Corpus, id: Int, typewriter: Typewriter) : Monkey(id, typewriter)