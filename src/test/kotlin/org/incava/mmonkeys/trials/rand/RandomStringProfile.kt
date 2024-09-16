package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType

class RandomStringProfile(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)

    fun addFilters(name: String, generator: StrRand) {
        // val filters = listOf(5, 10, 20)
        val filters = listOf(5, 10, 25)
        profiler.add(name) { generator.get() }
        filters.forEach { num -> profiler.add("$name $num") { generator.get(num) } }
    }

    fun profile(args: List<String>) {
        val calcList = StrCalcList()
        val calcMap = StrCalcMap()
        val jdk = StrJdk()
        val kt = StrKt()
        val genMap = StrGenMap()
        val genList = StrGenList()
        val calcLongDecode = StrCalcLongDecode()
        val calcBigIntOnly = StrCalcBigIntOnly()
        val bigIntToggle = StrCalcBigIntToggle()
        val mapping = mapOf(
            "kt - append" to kt,
            "jdk - append" to jdk,
            "gen - list - append" to genList,
            "gen - map - append" to genMap,
            "calc - list - append" to calcList,
            "calc - map - append" to calcMap,
            "calc - long - decode" to calcLongDecode,
            "calc - big - decode (only)," to calcBigIntOnly,
            "calc - big - decode (toggle)," to bigIntToggle,
        )
        if (args.isEmpty()) {
            mapping.forEach { addFilters(it.key, it.value) }
        } else {
            args.forEach {
                val generator = mapping[it] ?: throw IllegalArgumentException("invalid option: $it")
                addFilters(it, generator)
            }
        }
        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        Console.info("calcLongDecode.overruns", calcLongDecode.overruns)
    }
}

fun main(args: Array<String>) {
    println("args: ${args.toList()}")
    val obj = RandomStringProfile(1_000_000L, 5)
    obj.profile(args.toList())
}