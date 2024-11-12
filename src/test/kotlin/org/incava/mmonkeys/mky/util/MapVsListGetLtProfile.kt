package org.incava.mmonkeys.mky.util

import org.incava.confile.Profiler
import org.incava.confile.SortType
import java.util.*
import kotlin.random.Random

class MapVsListGetLtProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    fun profile() {
        val list = (0..99).toList()
        val linkedHashMap = list.associateByTo(linkedMapOf()) { it }
        val hashMap = list.associateByTo(hashMapOf()) { it }
        val treeMap = list.associateByTo(TreeMap()) { it }
        val array = list.toTypedArray()

        val profiler = Profiler(numInvokes, trialInvokes)

        profiler.add("array []") {
            val index = getRandom()
            array[index]
        }

        profiler.add("array <") {
            val index = getRandom()
            if (index < 27) array[index]
        }

        profiler.add("list []") {
            val index = getRandom()
            list[index]
        }

        profiler.add("list <") {
            val index = getRandom()
            if (index < 27) list[index]
        }

        profiler.add("linked hash map []") {
            val index = getRandom()
            linkedHashMap[index]
        }

        profiler.add("linked hash map <") {
            val index = getRandom()
            if (index < 27) linkedHashMap[index]
        }

        profiler.add("hash map []") {
            val index = getRandom()
            hashMap[index]
        }

        profiler.add("hash map <") {
            val index = getRandom()
            if (index < 27) hashMap[index]
        }

        profiler.add("tree map []") {
            val index = getRandom()
            treeMap[index]
        }

        profiler.add("tree map <") {
            val index = getRandom()
            if (index < 27) treeMap[index]
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }

    fun getRandom() = Random.Default.nextInt(100)
}

fun main() {
    val obj = MapVsListGetLtProfile(100_000_000L, 5)
    obj.profile()
}