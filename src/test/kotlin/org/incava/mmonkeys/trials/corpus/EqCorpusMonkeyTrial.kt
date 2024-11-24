package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.EqMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val sought = listOf("abc", "cde", "efg")
    val typewriter = Typewriter(Keys.fullList())
    val obj = EqMonkey(1, typewriter, Corpus(sought))
    Console.info("obj", obj)
    Console.info("sought", obj.corpus)
    Console.info("sought.present?", !obj.corpus.isEmpty())
    val iterations = AtomicLong()
    while (!obj.corpus.isEmpty()) {
        val result = obj.check()
        iterations.incrementAndGet()
        if (iterations.get() % 1_000_000L == 0L) {
            Console.info("(1) iterations", iterations)
        }
        if (result.isMatch) {
            Console.info("(2) iterations", iterations)
            Console.info("sought", sought)
        }
    }
}