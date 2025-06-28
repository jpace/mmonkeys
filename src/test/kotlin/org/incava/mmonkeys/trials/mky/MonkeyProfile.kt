package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkey
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyManager
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.mky.number.NumbersMonkeyManager
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

data class ProfileParams(
    val numInvokes: Long,
    val matchGoal: Int,
    val minLength: Int,
    val maxLength: Int,
    val numTrials: Int,
    val includeLists: Boolean,
)

class ProfileScenario(val name: String, val words: List<String>, val monitor: CountingMonitor, private val matchGoal: Int) {
    fun addTo(profiler: Profiler, manager: CountingMonitor, monkey: Monkey) {
        profiler.add(name) {
            while (manager.matches < matchGoal) {
                monkey.type()
            }
        }
    }
}

private class MonkeyProfile(private val params: ProfileParams) {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        .filter { it.length in params.minLength..params.maxLength }
    val profiler = Profiler(params.numInvokes, params.numTrials)
    val scenarios = mutableMapOf<String, ProfileScenario>()

    fun createManager() = CountingMonitor()
    fun dualCorpus() = DualCorpus(words)
    fun mapCorpus() = MapCorpus(words)
    fun numberedCorpus() = NumberedCorpus(words)
    fun sequences() = SequencesFactory.createFromWords(words)

    fun defaultMonkeyManager(corpus: WordCorpus, manager: CountingMonitor): DefaultMonkeyManager {
        return DefaultMonkeyManager(manager, corpus)
    }

    fun addWordGeneratorScenario() {
        val corpus = dualCorpus()
        val manager = createManager()
        val mgr = WordsGeneratorMonkeyManager(manager, corpus)
        val monkey = mgr.createMonkey()
        val scenario = ProfileScenario("words gen", words, manager, params.matchGoal)
        addScenario(scenario, manager, monkey)
    }

    fun addScenario(scenario: ProfileScenario, manager: CountingMonitor, monkey: DefaultMonkey) {
        scenario.addTo(profiler, manager, monkey)
        scenarios[scenario.name] = scenario
    }

    fun addScenario(scenario: ProfileScenario, manager: CountingMonitor, monkey: NumbersMonkey) {
        scenario.addTo(profiler, manager, monkey)
        scenarios[scenario.name] = scenario
    }

    fun addScenario(scenario: ProfileScenario, manager: CountingMonitor, monkey: WordsGeneratorMonkey) {
        scenario.addTo(profiler, manager, monkey)
        scenarios[scenario.name] = scenario
    }

    fun addSequenceScenario(name: String, supplier: (Sequences) -> TypeStrategy) {
        val sequences = sequences()
        val strategy = supplier(sequences)
        val corpus = mapCorpus()
        val manager = createManager()
        val mgr = defaultMonkeyManager(corpus, manager)
        val monkey = mgr.createMonkey(strategy)
        val scenario = ProfileScenario(name, words, manager, params.matchGoal)
        addScenario(scenario, manager, monkey)
    }

    fun addNumbered() {
        val corpus = numberedCorpus()
        val manager = createManager()
        val mgr = NumbersMonkeyManager(manager, corpus)
        val monkey = mgr.createMonkey()
        val scenario = ProfileScenario("numbers", words, manager, params.matchGoal)
        addScenario(scenario, manager, monkey)
    }

    fun addRandom() {
        val corpus = mapCorpus()
        val manager = createManager()
        val mgr = defaultMonkeyManager(corpus, manager)
        val monkey = mgr.createMonkeyRandom()
        val scenario = ProfileScenario("random", words, manager, params.matchGoal)
        addScenario(scenario, manager, monkey)
    }

    fun profile() {
        Console.info("words.#", words.size)

        addWordGeneratorScenario()
        addSequenceScenario("twos random", ::TwosRandomStrategy)
        addSequenceScenario("threes random", ::ThreesRandomStrategy)
        addSequenceScenario("twos distributed", ::TwosDistributedStrategy)
        addSequenceScenario("threes distributed", ::ThreesDistributedStrategy)
        addNumbered()
        addRandom()

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)
        scenarios.forEach { (name, scenario) ->
            Qlog.info("name", name)
            Qlog.info("monitor.attempts.#", scenario.monitor.attempts)
            Qlog.info("monitor.matches.#", scenario.monitor.matches)
            Qlog.info("monitor.keystrokes", scenario.monitor.byKeystrokes.toSortedMap())
        }

        val showdown = profiler.spawn()
        showdown.runAll()
        profiler.showResults(SortType.BY_DURATION)
        showdown.showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val params = ProfileParams(1L, 1000, 4, 13, 1, true)
    val obj = MonkeyProfile(params)
    obj.profile()
}