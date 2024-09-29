package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import java.util.Map.entry
import kotlin.test.currentStackTrace

class RandomStringProfile(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)

    fun addFilters(name: String, generator: StrRand) {
        // val filters = listOf(5, 10, 20)
        val filters = listOf(5, 10, 15, 25)
        val lettersToCount = mapOf(
            1 to 22,
            2 to 130,
            3 to 681,
            4 to 2087,
            5 to 3379,
            6 to 4446,
            7 to 4661,
            8 to 3920,
            9 to 2789,
            10 to 1773,
            11 to 992,
            12 to 440,
            13 to 202,
            14 to 47,
            15 to 27,
            16 to 2,
            17 to 3,
            27 to 1,
        )
        profiler.add(name) { generator.get() }
        filters.forEach { num -> profiler.add("$name $num") { generator.get(num) } }
    }

    fun profile(args: List<String>) {
        val calcList = StrCalcList()
        val calcListBytes = StrCalcListBytes()
        val calcMap = StrCalcMap()
        val genMapBuilder = StrGenMap()
        val genMapBytes = StrGenMapBytes()
        val genList = StrGenList()
        val calcLongDecode = StrCalcLongDecode()
        val calcBigIntOnly = StrCalcBigIntOnly()
        val bigIntToggle = StrCalcBigIntToggle()
        val mapping = mapOf(
//            "kt - append" to kt,
//            "jdk - append" to jdk,
            "gen - list - builder" to genList,
            "gen - map - builder" to genMapBuilder,
            "gen - map - bytes" to genMapBytes,
            "calc - list - builder" to calcList,
            "calc - map - builder" to calcMap,
            "calc - list - bytes" to calcListBytes,
            "calc - long - decode" to calcLongDecode,
            "calc - big - decode (only)," to calcBigIntOnly,
            "calc - big - decode (toggle)," to bigIntToggle,
        )
        val map2 = mapOf(
            "kt" to ::StrKt,
            "gen map builder" to ::StrGenMap,
            "gen list builder" to ::StrGenList,
            "calc map builder" to ::StrCalcMap,
            "calc list builder" to ::StrCalcList,
            "gen map bytes" to ::StrGenMapBytes,
            "gen list builder" to ::StrGenList,
            "calc map builder" to ::StrCalcMap,
            "calc list bytes" to ::StrCalcListBytes,
            "calc long decode" to ::StrCalcLongDecode,
            "calc big decode (only)" to ::StrCalcBigIntOnly,
            "calc big decode (toggle)" to ::StrCalcBigIntToggle,
        )
        val filtered = mutableMapOf<String, StrLenRand>()

        map2.forEach { (key, entry) ->
            val gen1 = entry()
            profiler.add(key) { gen1.get() }
            val gen2 = entry()
            // we only match 18 lengths (1 ... 17, 27 "honorificabilitudinitatibus")
            profiler.add("$key 18") { gen2.get(18) }
            if (gen2 is StrLenRand) {
                filtered["$key 18"] = gen2
            }
        }
//        if (args.isEmpty()) {
//            // mapping.forEach { addFilters(it.key, it.value) }
//        } else {
//            args.forEach {
//                val generator = mapping[it] ?: throw IllegalArgumentException("invalid option: $it")
//                addFilters(it, generator)
//            }
//        }
        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        Console.info("calcLongDecode.overruns", calcLongDecode.overruns)
        filtered.forEach { (name, gen) ->
            Console.info("$name filtered", gen.filtered)
        }

        val showdown = profiler.spawn(profiler.functions.size / 3, 5)
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)

        val showdown2 = profiler.spawn(showdown.functions.size / 3, 5)
        showdown2.runAll()
        showdown2.showResults(SortType.BY_INSERTION)
    }
}

fun main(args: Array<String>) {
    println("args: ${args.toList()}")
    val obj = RandomStringProfile(1_000_000L, 5)
    obj.profile(args.toList())
}