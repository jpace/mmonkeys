package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Attempt

interface AttemptObserver<T> {
    fun onSuccess(observed: T, attempt: Attempt)
    fun onFailed(observed: T, attempt: Attempt)
}
