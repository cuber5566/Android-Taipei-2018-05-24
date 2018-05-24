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
import io.taipei.androidtaipei20180524.vm.PhoneVerifyViewModel
import kotlinx.android.synthetic.main.fragment_phone_verify.*

class VerifyPhoneFragment : Fragment() {

    private lateinit var callback: Callback

    private lateinit var phoneVerifyViewModel: PhoneVerifyViewModel

    private lateinit var phoneNumber: String
    private lateinit var verifyId: String

    companion object {

        private const val EXTRA_PHONE_NUMBER = "EXTRA_PHONE_NUMBER"

        private const val SAVE_VERIFY_ID = "SAVE_VERIFY_ID"

        fun newInstance(phoneNumber: String): VerifyPhoneFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_PHONE_NUMBER, phoneNumber)
            val fragment = VerifyPhoneFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    interface Callback {
        fun onPhoneVerify(verifyId: String)
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
            phoneNumber = getString(EXTRA_PHONE_NUMBER)
        }

        savedInstanceState?.run {
            verifyId = getString(SAVE_VERIFY_ID)
        }

        phoneVerifyViewModel = AppInjector.obtainViewModel(this)

        phoneVerifyViewModel.phoneVerifySuccess.observe(this, Observer { onPhoneVerifySuccess(it!!) })
        phoneVerifyViewModel.phoneVerifyProgress.observe(this, Observer { onPhoneVerifyProgress(it!!) })
        phoneVerifyViewModel.phoneVerifyError.observe(this, Observer { onPhoneVerifyError(it!!) })

        phoneVerifyViewModel.checkVerifySuccess.observe(this, Observer { onCheckVerifySuccess() })
        phoneVerifyViewModel.checkVerifyProgress.observe(this, Observer { onCheckVerifyProgress(it!!) })
        phoneVerifyViewModel.checkVerifyError.observe(this, Observer { onCheckVerifyError(it!!) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, "取得驗證碼中...", Toast.LENGTH_LONG).show()
        phoneVerifyViewModel.getSMSVerifyCode(phoneNumber)

        sendButton.setOnClickListener {
            phoneVerifyViewModel.checkSMSVerifyCode(verifyId, smsVerifyCodeEdit.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVE_VERIFY_ID, verifyId)
    }

    private fun onPhoneVerifySuccess(verifyId: String) {
        /* do something */
        Toast.makeText(context, "取得驗證碼:$verifyId", Toast.LENGTH_LONG).show()
        this.verifyId = verifyId
    }

    private fun onPhoneVerifyProgress(progress: Boolean) {
        if (progress) {
            /* showProgress */
            progressBar.visibility = View.VISIBLE
        } else {
            /* hideProgress */
            progressBar.visibility = View.GONE
        }
    }

    private fun onPhoneVerifyError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    private fun onCheckVerifySuccess() {
        Toast.makeText(context, "電話驗證成功。", Toast.LENGTH_LONG).show()
        callback.onPhoneVerify(verifyId)
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