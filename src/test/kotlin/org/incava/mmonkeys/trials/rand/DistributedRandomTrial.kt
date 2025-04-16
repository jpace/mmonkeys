package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.CorpusTraits
import org.incava.mmonkeys.rand.DistributedRandom
import org.incava.mmonkeys.util.ResourceUtil

class DistributedRandomTrial {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length > 5 }
    val counts = CorpusTraits(words).characterCounts()

    fun counts() {
        Qlog.info("counts", counts)
    }

    fun unsorted() {
        val obj = DistributedRandom(counts)
        Qlog.info("obj.slots", obj.slots)
    }

    fun sorted() {
        val obj = DistributedRandom(counts.toSortedMap())
        Qlog.info("obj.slots", obj.slots)
    }

    fun evenWeighting() {
        val obj = DistributedRandom(counts.mapValues { 1 })
        Qlog.info("obj.slots", obj.slots)
    }
}

fun main() {
    val obj = DistributedRandomTrial()
    obj.counts()
    obj.evenWeighting()
    obj.unsorted()
    obj.sorted()
}