package org.incava.mmonkeys.trials.ui.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus

class MatchConsoleView<T : Corpus>(corpus: T, verbose: Boolean) : MatchView<T>(corpus, verbose) {
    override fun showResult(monkey: Monkey, result: Int?) {
        Console.info("result.match?", result != null)
        Console.info("sought.matched.#", corpus.matched.size)
        Console.info("sought.words.#", corpus.words.size)
        Console.info("sought.unmatched?", corpus.hasUnmatched())
        monkey.manager?.summarize()
        if (result != null) {
            Console.info("monkey.class", monkey.javaClass)
            Console.info("result.keystrokes", corpus.words[result].length)
            Console.info("result.index", result)
            Console.info("word", corpus.words[result])
        }
    }
}
