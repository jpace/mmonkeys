package org.incava.mmonkeys.trials.rand

class RandomStringProfile(numInvokes: Long, trialInvokes: Int) : RandomStringProfileBase(numInvokes, trialInvokes) {
    init {
        functions.forEach { (key, entry) ->
            val gen = entry()
            profiler.add(key) { gen.get() }
        }
        profiler.add("jdk", ::StrJdk)
        profiler.add("kt", ::StrKt)
    }
}

fun main() {
    val obj = RandomStringProfile(1_000_000L, 3)
    obj.profile()
}