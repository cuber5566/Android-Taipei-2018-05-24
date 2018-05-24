package io.taipei.androidtaipei20180524

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.taipei.androidtaipei20180524.provider.scheduler.AppScheduler
import io.taipei.androidtaipei20180524.provider.scheduler.SchedulerProvider
import io.taipei.androidtaipei20180524.repository.RegisterRepository
import io.taipei.androidtaipei20180524.vm.ViewModelFactory

object AppInjector {

    lateinit var application: Application private set

    lateinit var appScheduler: AppScheduler private set

    lateinit var viewModelFactory: ViewModelFactory private set
    lateinit var registerRepository: RegisterRepository private set

    fun init(application: Application) {
        this.application = application

        appScheduler = SchedulerProvider
        registerRepository = RegisterRepository()
        viewModelFactory = ViewModelFactory(application, appScheduler, registerRepository)
    }

    inline fun <reified T : ViewModel> obtainViewModel(activity: Activity): T =
            ViewModelProviders.of(activity as AppCompatActivity, viewModelFactory).get(T::class.java)

    inline fun <reified T : ViewModel> obtainViewModel(fragment: Fragment): T =
            ViewModelProviders.of(fragment, viewModelFactory).get(T::class.java)
}