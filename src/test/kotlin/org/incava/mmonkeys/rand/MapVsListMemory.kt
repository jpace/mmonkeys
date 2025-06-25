package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.ikdk.util.MapUtil
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

class MapVsListMemory {
    private fun addToMap(map: MutableMap<Char, MutableMap<Char, Int>>, ch1: Char, ch2: Char, number: Int) {
        MapUtil.ensureMap(map, ch1)
            .also { map1 -> map1[ch2] = number }
    }

    private fun addToMap(
        map: MutableMap<Char, MutableMap<Char, MutableMap<Char, Int>>>,
        ch1: Char,
        ch2: Char,
        ch3: Char,
        number: Int,
    ) {
        MapUtil.ensureMap(map, ch1)
            .also { map1 -> addToMap(map1, ch2, ch3, number) }
    }

    private fun addToMap(
        map: MutableMap<Char, MutableMap<Char, MutableMap<Char, MutableMap<Char, Int>>>>,
        ch1: Char,
        ch2: Char,
        ch3: Char,
        ch4: Char,
        number: Int,
    ) {
        MapUtil.ensureMap(map, ch1)
            .also { map1 ->
                addToMap(map1, ch2, ch3, ch4, number)
            }
    }

    private fun nest2(block: (Char, Char, Int) -> Unit) {
        val count = Keys.fullList().size
        repeat(count) {
            val ch1 = Keys.fullList().random()
            repeat(count) {
                val ch2 = Keys.fullList().random()
                val number = Random.Default.nextInt()
                block(ch1, ch2, number)
            }
        }
    }

    fun nest3(block: (Char, Char, Char, Int) -> Unit) {
        val count = Keys.fullList().size
        repeat(count) {
            val ch1 = Keys.fullList().random()
            repeat(count) {
                val ch2 = Keys.fullList().random()
                repeat(count) {
                    val ch3 = Keys.fullList().random()
                    val number = Random.Default.nextInt()
                    block(ch1, ch2, ch3, number)
                }
            }
        }
    }

    fun nest4(block: (Char, Char, Char, Char, Int) -> Unit) {
        val count = Keys.fullList().size
        repeat(count) {
            val ch1 = Keys.fullList().random()
            repeat(count) {
                val ch2 = Keys.fullList().random()
                repeat(count) {
                    val ch3 = Keys.fullList().random()
                    repeat(count) {
                        val ch4 = Keys.fullList().random()
                        val number = Random.Default.nextInt()
                        block(ch1, ch2, ch3, ch4, number)
                    }
                }
            }
        }
    }

    fun createData2List() {
        val list = MapVsListProfile.ListMap()
        nest2 { x, y, count ->
            list[listOf(x, y)] = count
        }
    }

    fun createData2Map() {
        val map = MapVsListProfile.Map2()
        nest2 { x, y, count ->
            addToMap(map, x, y, count)
        }
    }

    fun createData2Pair() {
        val list = MapVsListProfile.PairMap()
        nest2 { x, y, count ->
            list[Pair(x, y)] = count
        }
    }

    fun createData3List() {
        val list = MapVsListProfile.ListMap()
        nest3 { x, y, z, count ->
            list[listOf(x, y, z)] = count
        }
    }

    fun createData3Map() {
        val map = MapVsListProfile.Map3()
        nest3 { x, y, z, count ->
            addToMap(map, x, y, z, count)
        }
    }

    fun createData4List() {
        val list = MapVsListProfile.ListMap()
        nest4 { ch1, ch2, ch3, ch4, count ->
            list[listOf(ch1, ch2, ch3, ch4)] = count
        }
    }

    fun createData4Map() {
        val map = MapVsListProfile.Map4()
        nest4 { ch1, ch2, ch3, ch4, count ->
            addToMap(map, ch1, ch2, ch3, ch4, count)
        }
    }
}

fun main() {
    Qlog.info("starting")
    val runList = false
    val obj = MapVsListMemory()
    val memory = Memory()
    var done = false
    val thread = Thread {
        memory.showBanner()
        var iteration = AtomicLong()
        while (!done) {
            memory.showCurrent(iteration)
            iteration.incrementAndGet()
            Thread.sleep(50L)
        }
    }
    Qlog.info("starting thread")
    thread.start()
    Qlog.info("initial sleep")
    Thread.sleep(1_000L)
    Qlog.info("creating")
    if (runList) {
        obj.createData2List()
//        obj.createData3List()
//        obj.createData4List()
    } else {
        obj.createData2Pair()
//        obj.createData2Map()
//        obj.createData3Map()
//        obj.createData4Map()
    }
    Qlog.info("done creating")
    Qlog.info("sleeping")
    Thread.sleep(5_000L)
    Qlog.info("done sleeping")
    done = true
    Qlog.info("ending")
}