package io.taipei.androidtaipei20180524.provider.scheduler

import io.reactivex.Scheduler

/**
 * Allow providing different types of [Scheduler]s.
 */
interface AppScheduler {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler

    fun single():Scheduler

    fun trampoline():Scheduler
}
