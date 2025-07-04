package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

class WordChecker(val corpus: WordCorpus, private val manager: Manager) {
    fun check(monkey: Monkey, str: String) {
        val index = corpus.findMatch(str)
        val attempt = if (index == null) {
            AttemptFactory.failed(str)
        } else {
            corpus.setMatched(index, str)
            val word = Word(str, index)
            AttemptFactory.succeeded(word)
        }
        manager.update(monkey, attempt)
    }
}
