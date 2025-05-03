package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class SequencesProfile {
    val sequences: Sequences

    init {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        sequences = SequencesFactory.createFromWords(words)
    }


    fun twos() {
        val sorted = sequences.twos.mapValues { it.value.toSortedMap() }.toSortedMap()
        Qlog.info("twos", sorted)
    }

    fun threes() {
        Qlog.info("threes", sequences.threes.mapValues { x -> x.value.mapValues { y -> y.value.toSortedMap() }.toSortedMap() }.toSortedMap())
    }
}

fun main() {
    val obj = SequencesProfile()
    obj.twos()
    obj.threes()
}