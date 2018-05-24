package io.taipei.androidtaipei20180524.vm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.taipei.androidtaipei20180524.provider.scheduler.AppScheduler
import io.reactivex.disposables.CompositeDisposable
import io.taipei.androidtaipei20180524.repository.RegisterRepository

/**
 * [ViewModelFactory] provide all ViewModels of this app
 * */
class ViewModelFactory(
        private val application: Application,
        private val appScheduler: AppScheduler,
        private val registerRepository: RegisterRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(RegisterViewModel::class.java) ->
                    RegisterViewModel(application, appScheduler, CompositeDisposable(), registerRepository)

                isAssignableFrom(PhoneVerifyViewModel::class.java) ->
                    PhoneVerifyViewModel(application, appScheduler, CompositeDisposable(), registerRepository)

                isAssignableFrom(EmailVerifyViewModel::class.java) ->
                    EmailVerifyViewModel(application, appScheduler, CompositeDisposable(), registerRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}