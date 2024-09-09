package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType

class RandomStringProfile(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)

    fun addFilters(name: String, generator: StrRand) {
        // val filters = listOf(5, 10, 20)
        val filters = listOf(10)
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
        args.forEach {
            when (it) {
                "kt - append" -> addFilters(it, kt)
                "jdk - append" -> addFilters(it, jdk)
                "gen - list - append" -> addFilters(it, genList)
                "gen - map - append" -> addFilters(it, genMap)
                "calc - list - append" -> addFilters(it, calcList)
                "calc - map - append" -> addFilters(it, calcMap)
                "calc - int - decode" -> addFilters(it, calcLongDecode)
                "calc - big - decode (only)" -> addFilters(it, calcBigIntOnly)
                "calc - big - decode (toggle)" -> addFilters(it, bigIntToggle)

                else -> println("it?: $it")
            }
        }
        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        Console.info("calcLongDecode.overruns", calcLongDecode.overruns)
    }
}

fun main(args: Array<String>) {
    println("args: ${args.toList()}")
    val toRun = args.ifEmpty { arrayOf("kt", "jdk", "calc map", "cal list", "gen") }
    val obj = StrFactoryProfile(1_000_000L, 3)
    obj.profile()
}