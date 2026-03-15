package org.incava.mmonkeys.rand

open class CharIntMap3(val map: Map<Char, Map<Char, MapCharToCount>>)  {
    val profile: CharsProfile = CharsProfile(map)
    val random: CharsRandomProfile
        get() = profile.random
    val dist: CharsDistProfile
        get() = profile.dist
}