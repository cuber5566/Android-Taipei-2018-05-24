package io.taipei.androidtaipei20180524.repository

import io.reactivex.Completable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class RegisterRepository {

    companion object {
        private const val FAKE_DELAY = 2_000L
    }

    fun register(phoneNumber: String, email: String, password: String): Completable {
        return Completable.complete().delay(FAKE_DELAY, TimeUnit.MILLISECONDS)
    }

    fun getSmsVerifyCode(phoneNumber: String): Single<String> {
        return Single.just("9487").delay(FAKE_DELAY, TimeUnit.MILLISECONDS)
    }

    fun checkSmsVerifyCode(verifyId: String, verifyCode: String): Completable {
        return Completable.complete().delay(FAKE_DELAY, TimeUnit.MILLISECONDS)
    }

    fun getEmailVerifyCode(email: String): Single<String> {
        return Single.just("0857").delay(FAKE_DELAY, TimeUnit.MILLISECONDS)
    }

    fun checkEmailVerifyCode(verifyId: String, verifyCode: String): Completable {
        return Completable.complete().delay(FAKE_DELAY, TimeUnit.MILLISECONDS)
    }
}