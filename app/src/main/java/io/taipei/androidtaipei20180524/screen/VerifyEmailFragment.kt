package io.taipei.androidtaipei20180524.screen

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.taipei.androidtaipei20180524.AppInjector
import io.taipei.androidtaipei20180524.R
import io.taipei.androidtaipei20180524.vm.EmailVerifyViewModel
import kotlinx.android.synthetic.main.fragment_email_verify.*

class VerifyEmailFragment : Fragment() {

    private lateinit var callback: Callback

    private lateinit var emailVerifyViewModel: EmailVerifyViewModel

    private lateinit var email: String
    private lateinit var verifyId: String

    companion object {

        private const val EXTRA_EMAIL_NUMBER = "EXTRA_EMAIL_NUMBER"

        private const val SAVE_VERIFY_ID = "SAVE_VERIFY_ID"

        fun newInstance(email: String): VerifyEmailFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_EMAIL_NUMBER, email)
            val fragment = VerifyEmailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    interface Callback {
        fun onEmailVerify(verifyId: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as Callback
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement ${this}.Callback")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.run {
            email = getString(EXTRA_EMAIL_NUMBER)
        }

        savedInstanceState?.run {
            verifyId = getString(SAVE_VERIFY_ID)
        }

        emailVerifyViewModel = AppInjector.obtainViewModel(this)

        emailVerifyViewModel.emailVerifySuccess.observe(this, Observer { onEmailVerifySuccess(it!!) })
        emailVerifyViewModel.emailVerifyProgress.observe(this, Observer { onEmailVerifyProgress(it!!) })
        emailVerifyViewModel.emailVerifyError.observe(this, Observer { onEmailVerifyError(it!!) })

        emailVerifyViewModel.checkVerifySuccess.observe(this, Observer { onCheckVerifySuccess() })
        emailVerifyViewModel.checkVerifyProgress.observe(this, Observer { onCheckVerifyProgress(it!!) })
        emailVerifyViewModel.checkVerifyError.observe(this, Observer { onCheckVerifyError(it!!) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_email_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, "取得驗證碼中...", Toast.LENGTH_LONG).show()
        emailVerifyViewModel.getEmailVerifyCode(email)

        sendButton.setOnClickListener {
            emailVerifyViewModel.checkEmailVerifyCode(verifyId, emailVerifyCodeEdit.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVE_VERIFY_ID, verifyId)
    }

    private fun onEmailVerifySuccess(verifyId: String) {
        /* do something */
        Toast.makeText(context, "取得驗證碼:$verifyId", Toast.LENGTH_LONG).show()
        this.verifyId = verifyId
    }

    private fun onEmailVerifyProgress(progress: Boolean) {
        if (progress) {
            /* showProgress */
            progressBar.visibility = View.VISIBLE
        } else {
            /* hideProgress */
            progressBar.visibility = View.GONE
        }
    }

    private fun onEmailVerifyError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    private fun onCheckVerifySuccess() {
        Toast.makeText(context, "Email驗證成功。", Toast.LENGTH_LONG).show()
        callback.onEmailVerify(verifyId)
    }

    private fun onCheckVerifyProgress(progress: Boolean) {
        if (progress) {
            /* showProgress */
            progressBar.visibility = View.VISIBLE
        } else {
            /* hideProgress */
            progressBar.visibility = View.GONE
        }
    }

    private fun onCheckVerifyError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}