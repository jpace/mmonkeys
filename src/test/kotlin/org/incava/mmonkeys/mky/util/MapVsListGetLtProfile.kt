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
            val index = Random.Default.nextInt(100)
            array[index]
        }

        profiler.add("array <") {
            val index = Random.Default.nextInt(100)
            if (index < 27) array[index] else 1
        }

        profiler.add("list []") {
            val index = Random.Default.nextInt(100)
            list[index]
        }

        profiler.add("list <") {
            val index = Random.Default.nextInt(100)
            if (index < 27) list[index] else 1
        }

        profiler.add("linked hash map []") {
            val index = Random.Default.nextInt(100)
            linkedHashMap[index]
        }

        profiler.add("linked hash map <") {
            val index = Random.Default.nextInt(100)
            if (index < 27) linkedHashMap[index] else 1
        }

        profiler.add("hash map []") {
            val index = Random.Default.nextInt(100)
            hashMap[index]
        }

        profiler.add("hash map <") {
            val index = Random.Default.nextInt(100)
            if (index < 27) hashMap[index] else 1
        }

        profiler.add("tree map []") {
            val index = Random.Default.nextInt(100)
            treeMap[index]
        }

        profiler.add("tree map <") {
            val index = Random.Default.nextInt(100)
            if (index < 27) treeMap[index] else 1
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults()
    }
}

fun main() {
    val obj = MapVsListGetLtProfile(100_000_000L, 5)
    obj.profile()
}