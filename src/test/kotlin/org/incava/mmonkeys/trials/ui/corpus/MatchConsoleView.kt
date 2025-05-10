package org.incava.mmonkeys.trials.ui.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.corpus.Corpus

class MatchConsoleView<T : Corpus>(corpus: T, verbose: Boolean) : MatchView<T>(corpus, verbose) {
    override fun showResult(monkey: Monkey, manager: MonkeyMonitor, result: Int?) {
        Console.info("result.match?", result != null)
        Console.info("sought.matched.#", corpus.matches().size)
        Console.info("sought.words.#", corpus.numWords())
        Console.info("sought.unmatched?", corpus.hasUnmatched())
        manager.summarize()
        if (result != null) {
            Console.info("monkey.class", monkey.javaClass)
            Console.info("result.keystrokes", corpus.lengthAtIndex(result))
            Console.info("result.index", result)
            Console.info("word", corpus.wordAtIndex(result))
        }
    }
}
