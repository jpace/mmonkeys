package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType

open class RandomStringProfileBase(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)
    val functions: Map<String, ()-> StrRand>

    init {
        val obj = StrRandFactory.create(StrRandFactory.genMap, StrRandFactory.build)

        functions = mapOf(
            "calc map builder" to StrRandFactory::calcMapBuild,
            "calc map assemble" to StrRandFactory::calcMapAssemble,
            "calc map buffer" to StrRandFactory::calcMapBuffer,

            "calc list builder" to StrRandFactory::calcListBuild,
            "calc list assemble" to StrRandFactory::calcListAssemble,
            "calc list buffer" to StrRandFactory::calcListBuffer,

            "gen map builder" to { obj },
            "gen map assemble" to StrRandFactory::genMapAssemble,
            "gen map buffer" to StrRandFactory::genMapBuffer,

            "gen list builder" to StrRandFactory::genListBuild,
            "gen list assemble" to StrRandFactory::genListAssemble,
            "gen list buffer" to StrRandFactory::genListBuffer,

            "calc long decode" to ::StrCalcLongDecode,
            "calc big decode (only)" to ::StrCalcBigIntOnly,
            "calc big decode (toggle)" to ::StrCalcBigIntToggle,
        )
    }

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

    open fun profile() {
        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)

        val showdown2 = showdown.spawn()
        showdown2.runAll()
        showdown2.showResults(SortType.BY_DURATION)
    }
}
