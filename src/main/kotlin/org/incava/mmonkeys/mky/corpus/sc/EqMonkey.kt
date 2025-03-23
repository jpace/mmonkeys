package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus

class EqMonkey(id: Int, corpus: Corpus) : DefaultMonkey(id, corpus, StrategyFactory.fullRandom())