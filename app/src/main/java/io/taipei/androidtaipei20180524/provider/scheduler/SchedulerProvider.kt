package io.taipei.androidtaipei20180524.provider.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.taipei.androidtaipei20180524.provider.scheduler.AppScheduler

/**
 * Provides different types of [Schedulers].
 */
object SchedulerProvider : AppScheduler {
    override fun single(): Scheduler {
        return Schedulers.single()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
