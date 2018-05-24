package io.taipei.androidtaipei20180524.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.taipei.androidtaipei20180524.SingleLiveEvent
import io.taipei.androidtaipei20180524.provider.scheduler.AppScheduler
import io.taipei.androidtaipei20180524.repository.RegisterRepository

class PhoneVerifyViewModel(
        application: Application,
        private val appScheduler: AppScheduler,
        private val compositeDisposable: CompositeDisposable,
        private val registerRepository: RegisterRepository
) : AndroidViewModel(application) {

    val phoneVerifySuccess = MutableLiveData<String>()
    val phoneVerifyProgress = MutableLiveData<Boolean>()
    val phoneVerifyError = SingleLiveEvent<Throwable>()

    val checkVerifySuccess = MutableLiveData<Void>()
    val checkVerifyProgress = MutableLiveData<Boolean>()
    val checkVerifyError = SingleLiveEvent<Throwable>()

    fun getSMSVerifyCode(phoneNumber: String) {
        compositeDisposable.add(registerRepository.getSmsVerifyCode(phoneNumber)
                .observeOn(appScheduler.ui())
                .doOnSubscribe { phoneVerifyProgress.value = true }
                .doFinally { phoneVerifyProgress.value = false }
                .subscribe(
                        { phoneVerifySuccess.value = it },
                        { phoneVerifyError.value = it }
                )
        )
    }

    fun checkSMSVerifyCode(verifyId: String, verifyCode: String) {
        compositeDisposable.add(registerRepository.checkSmsVerifyCode(verifyId, verifyCode)
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