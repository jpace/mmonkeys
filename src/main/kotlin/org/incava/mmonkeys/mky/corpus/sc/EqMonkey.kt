package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys

class EqMonkey(id: Int, corpus: Corpus) : CorpusMonkey(id, corpus, RandomStrategy(Keys.fullList()))