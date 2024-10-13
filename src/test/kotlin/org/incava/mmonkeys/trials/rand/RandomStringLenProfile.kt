package org.incava.mmonkeys.trials.rand

class RandomStringLenProfile(numInvokes: Long, trialInvokes: Int) : RandomStringProfileBase(numInvokes, trialInvokes) {
    init {
        functions.forEach { (key, entry) ->
            val gen = entry()
            // we only match 18 lengths (1 ... 17, 27 "honorificabilitudinitatibus")
            profiler.add("$key 18") { gen.get(18) }
        }
    }
}

private fun main() {
    val obj = RandomStringLenProfile(1_000_000L, 5)
    obj.profile()
}