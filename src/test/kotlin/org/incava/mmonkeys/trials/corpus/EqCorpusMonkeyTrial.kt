package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.CorpusMonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.EqCorpusMonkey
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val monkeyFactory = CorpusMonkeyFactory(ctor = ::EqCorpusMonkey)
    val sought = listOf("abc", "cde", "efg")
    val obj = monkeyFactory.createMonkey(Corpus(sought))
    Console.info("obj", obj)
    Console.info("sought", obj.sought)
    Console.info("sought.present?", !obj.sought.isEmpty())
    val iterations = AtomicLong()
    while (!obj.sought.isEmpty()) {
        val result = obj.check()
        iterations.incrementAndGet()
        if (iterations.incrementAndGet() % 1_000_000L == 0L) {
            Console.info("(1) iterations", iterations)
        }
        if (result.isMatch) {
            Console.info("(2) iterations", iterations)
            Console.info("sought", sought)
        }
    }
}