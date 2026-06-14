package org.incava.mmonkeys.chars

object CharsElementFactory {
    fun toMap(list: List<CharCount>): Map<Char, Int> {
        return list.associate { it.char to it.count }
    }

    fun toList(map: Map<Char, Int>): List<CharCount> {
        return map.map { CharCount(it.key, it.value) }
    }

    fun toMapToList(charToCharToCount: Map<Char, Map<Char, Int>>): Map<Char, List<CharCount>> {
        return charToCharToCount.mapValues { (_, charToCount) ->
            toList(charToCount)
        }
    }
}