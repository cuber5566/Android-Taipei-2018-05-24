package io.taipei.androidtaipei20180524.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.taipei.androidtaipei20180524.SingleLiveEvent
import io.taipei.androidtaipei20180524.provider.scheduler.AppScheduler
import io.taipei.androidtaipei20180524.repository.RegisterRepository

class EmailVerifyViewModel(
        application: Application,
        private val appScheduler: AppScheduler,
        private val compositeDisposable: CompositeDisposable,
        private val registerRepository: RegisterRepository
) : AndroidViewModel(application) {

    val emailVerifySuccess = MutableLiveData<String>()
    val emailVerifyProgress = MutableLiveData<Boolean>()
    val emailVerifyError = SingleLiveEvent<Throwable>()

    val checkVerifySuccess = MutableLiveData<Void>()
    val checkVerifyProgress = MutableLiveData<Boolean>()
    val checkVerifyError = SingleLiveEvent<Throwable>()

    fun getEmailVerifyCode(email: String) {
        compositeDisposable.add(registerRepository.getEmailVerifyCode(email)
                .observeOn(appScheduler.ui())
                .doOnSubscribe { emailVerifyProgress.value = true }
                .doFinally { emailVerifyProgress.value = false }
                .subscribe(
                        { emailVerifySuccess.value = it },
                        { emailVerifyError.value = it }
                )
        )
    }

    fun checkEmailVerifyCode(verifyId: String, verifyCode: String) {
        compositeDisposable.add(registerRepository.checkEmailVerifyCode(verifyId, verifyCode)
                .observeOn(appScheduler.ui())
                .doOnSubscribe { checkVerifyProgress.value = true }
                .doFinally { checkVerifyProgress.value = false }
                .subscribe(
                        { checkVerifySuccess.value = null },
                        { checkVerifyError.value = it }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}