package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val monkey = MonkeyFactory().createMonkey()
    val sought = listOf("abc", "cde", "efg")
    val obj = EqCorpusMatcher(monkey, Corpus(sought))
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