package io.taipei.androidtaipei20180524.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.taipei.androidtaipei20180524.SingleLiveEvent
import io.taipei.androidtaipei20180524.provider.scheduler.AppScheduler
import io.taipei.androidtaipei20180524.repository.RegisterRepository

class RegisterViewModel(
        application: Application,
        private val appScheduler: AppScheduler,
        private val compositeDisposable: CompositeDisposable,
        private val registerRepository: RegisterRepository
) : AndroidViewModel(application) {


    val registerSuccess = MutableLiveData<Void>()
    val registerProgress = MutableLiveData<Boolean>()
    val registerError = SingleLiveEvent<Throwable>()

    fun register(phoneNumber:String, email:String, password: String){
        compositeDisposable.add(registerRepository.register(phoneNumber, email, password)
                .observeOn(appScheduler.ui())
                .doOnSubscribe { registerProgress.value = true }
                .doFinally { registerProgress.value = false }
                .subscribe(
                        { registerSuccess.value = null },
                        { registerError.value = it }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}