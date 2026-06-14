package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.chars.CharCount
import org.incava.mmonkeys.chars.CharsCount
import org.incava.mmonkeys.chars.CharsElementFactory

object CharSlotsFactory {
    fun createSlots(charToCounts: Map<Char, List<CharCount>>): CharsSlots {
        return charToCounts.mapValues { (char, list) ->
            val charsCount = CharsCount(char, list)
            charsCount.count()
        }.let { CharsSlots(it) }
    }

    fun createMapToSlots(charToCharToCount: Map<Char, Map<Char, Int>>): Map<Char, CharsSlots> {
        return charToCharToCount.mapValues { createMapToSlots(it.value) }
    }

    fun createMapToSlots(counts: Map<Char, Int>): CharsSlots {
        val list = CharsElementFactory.toList(counts)
        return createListToSlots(list)
    }

    fun createListToSlots(list: List<CharCount>): CharsSlots {
        Qlog.info("list", list)
        val counts = CharsElementFactory.toMap(list)
        return CharsSlots(counts)
    }
}