package org.incava.mmonkeys.trials.ui.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus

class MatchConsoleView<T : Corpus>(corpus: T, verbose: Boolean) : MatchView<T>(corpus, verbose) {
    override fun showResult(monkey: Monkey, result: MatchData) {
        Console.info("result.match?", result.isMatch)
        Console.info("sought.matched.#", corpus.matched.size)
        Console.info("sought.words.#", corpus.words.size)
        Console.info("sought.empty?", corpus.isEmpty())
        monkey.monitors.forEach { it.summarize() }
        if (result.isMatch) {
            Console.info("monkey.class", monkey.javaClass)
            Console.info("result.keystrokes", result.keystrokes)
            Console.info("result.index", result.index)
            Console.info("word", corpus.words[result.index])
        }
    }
}
