package org.incava.mmonkeys.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Qlog
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.type.Keys
import kotlin.random.Random

class MapVsListProfile(val numInvokes: Long, val numTrials: Int) {
    class Map2 : LinkedHashMap<Char, MutableMap<Char, Int>>() {
        fun lookup(chars: List<Char>): Int? {
            return this[chars[0]]?.get(chars[1])
        }
    }

    class Map3 : LinkedHashMap<Char, MutableMap<Char, MutableMap<Char, Int>>>() {
        fun lookup(chars: List<Char>): Int? {
            return this[chars[0]]?.get(chars[1])?.get(chars[2])
        }
    }

    class Map4 : LinkedHashMap<Char, MutableMap<Char, MutableMap<Char, MutableMap<Char, Int>>>>() {
        fun lookup(chars: List<Char>): Int? {
            return this[chars[0]]?.get(chars[1])?.get(chars[2])?.get(chars[3])
        }
    }

    class ListMap : LinkedHashMap<List<Char>, Int>() {
        fun lookup(chars: List<Char>): Int? {
            return this[chars]
        }
    }

    class PairMap : LinkedHashMap<Pair<Char, Char>, Int>() {
        fun lookup(chars: List<Char>): Int? {
            val pair = Pair(chars.first(), chars.last())
            return this[pair]
        }
    }

    fun addToMap(map: MutableMap<Char, Int>, char: Char, number: Int) {
        map[char] = number
    }

    fun addToMap(map: MutableMap<Char, MutableMap<Char, Int>>, x: Char, y: Char, number: Int) {
        MapUtil.ensureMap(map, x)
            .also { map1 -> addToMap(map1, y, number) }
    }

    fun addToMap(
        map: MutableMap<Char, MutableMap<Char, MutableMap<Char, Int>>>,
        x: Char,
        y: Char,
        z: Char,
        number: Int,
    ) {
        MapUtil.ensureMap(map, x)
            .also { map1 -> addToMap(map1, y, z, number) }
    }

    fun createData2(): Pair<ListMap, Map2> {
        val list = ListMap()
        val map = Map2()
        val count = Keys.fullList().size
        repeat(count) {
            val ch1 = Keys.fullList().random()
            repeat(count) {
                val ch2 = Keys.fullList().random()
                val idx = Random.Default.nextInt()
                list[listOf(ch1, ch2)] = idx
                addToMap(map, ch1, ch2, idx)
            }
        }
        return list to map
    }

    fun createData3(): Pair<ListMap, Map3> {
        val list = ListMap()
        val map = Map3()
        val count = Keys.fullList().size
        repeat(count) {
            val ch1 = Keys.fullList().random()
            repeat(count) {
                val ch2 = Keys.fullList().random()
                repeat(count) {
                    val ch3 = Keys.fullList().random()
                    val idx = Random.Default.nextInt()
                    list[listOf(ch1, ch2, ch3)] = idx
                    addToMap(map, ch1, ch2, ch3, idx)
                }
            }
        }
        return list to map
    }

    fun createData4(): Pair<ListMap, Map4> {
        val list = ListMap()
        val map = Map4()
        val count = Keys.fullList().size
        repeat(count) {
            val ch1 = Keys.fullList().random()
            repeat(count) {
                val ch2 = Keys.fullList().random()
                repeat(count) {
                    val ch3 = Keys.fullList().random()
                    repeat(count) {
                        val ch4 = Keys.fullList().random()
                        val idx = Random.Default.nextInt()
                        list[listOf(ch1, ch2, ch3)] = idx
                        MapUtil.ensureMap(map, ch1)
                            .also { map1 ->
                                addToMap(map1, ch2, ch3, ch4, idx)
                            }
                    }
                }
            }
        }
        return list to map
    }

    fun profile2() {
        Qlog.info("setting up")
        val (list, map) = createData2()
        profile(2, list::lookup, map::lookup)
    }

    fun profile3() {
        Qlog.info("setting up")
        val (list, map) = createData3()
        profile(3, list::lookup, map::lookup)
    }

    fun profile4() {
        Qlog.info("setting up")
        val (list, map) = createData4()
        profile(4, list::lookup, map::lookup)
    }

    fun profile(numChars: Int, listLookup: ((List<Char>) -> Int?), mapLookup: ((List<Char>) -> Int?)) {
        val profiler = Profiler(numInvokes, numTrials)

        profiler.add("list $numChars") {
            val chars = (0 until numChars).map { Keys.fullList().random() }
            val result = listLookup(chars)
        }

        profiler.add("map $numChars") {
            val chars = (0 until numChars).map { Keys.fullList().random() }
            val result = mapLookup(chars)
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = MapVsListProfile(1_000_000L, 2)
    obj.profile2()
    obj.profile3()
    obj.profile4()
}