package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter

abstract class CorpusMonkey(open val corpus: Corpus, id: Int, typewriter: Typewriter) : Monkey(id, typewriter)