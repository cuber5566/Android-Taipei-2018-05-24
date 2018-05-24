package io.taipei.androidtaipei20180524.screen

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.taipei.androidtaipei20180524.AppInjector
import io.taipei.androidtaipei20180524.R
import io.taipei.androidtaipei20180524.vm.RegisterDataFragment
import io.taipei.androidtaipei20180524.vm.RegisterViewModel
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity(),
        RegisterDataFragment.Callback,
        VerifyPhoneFragment.Callback,
        VerifyEmailFragment.Callback {

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var phoneNumber: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var verifyId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerViewModel = AppInjector.obtainViewModel(this)
        registerViewModel.registerSuccess.observe(this, Observer { onRegisterSuccess() })
        registerViewModel.registerProgress.observe(this, Observer { onRegisterProgress(it!!) })
        registerViewModel.registerError.observe(this, Observer { onRegisterError(it!!) })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, RegisterDataFragment.newInstance())
                    .commit()
        }
    }

    override fun onGetRegisterData(phoneNumber: String, email: String, password: String) {
        this.phoneNumber = phoneNumber
        this.email = email
        this.password = password
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, VerifyPhoneFragment.newInstance(phoneNumber))
                .addToBackStack(null)
                .commit()
    }

    override fun onPhoneVerify(verifyId: String) {
        this.verifyId = verifyId
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, VerifyEmailFragment.newInstance(email))
                .addToBackStack(null)
                .commit()
    }

    override fun onEmailVerify(verifyId: String) {
        Toast.makeText(this, "註冊中...", Toast.LENGTH_LONG).show()
        registerViewModel.register(phoneNumber, email, password)
    }

    private fun onRegisterSuccess() {
        Toast.makeText(this, "註冊完成！", Toast.LENGTH_LONG).show()
    }

    private fun onRegisterProgress(progress: Boolean) {
        progressBar.visibility = if (progress) View.VISIBLE else View.GONE
    }

    private fun onRegisterError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
