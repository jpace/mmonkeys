package org.incava.mmonkeys.trials.match.corpus

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.string.EqCorpusMatcher
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val typewriter = StandardTypewriter(Keys.keyList('z'))
    val monkey = Monkey(1, typewriter)
    val sought = listOf("abc", "cde", "efg")
    val obj = EqCorpusMatcher(monkey, Corpus(sought))
    Console.info("obj", obj)
    Console.info("sought", obj.sought)
    Console.info("sought.present?", obj.sought.isNotEmpty())
    val iterations = AtomicLong()
    while (obj.sought.isNotEmpty()) {
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