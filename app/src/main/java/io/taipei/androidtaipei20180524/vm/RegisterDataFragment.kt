package io.taipei.androidtaipei20180524.vm

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.taipei.androidtaipei20180524.R
import kotlinx.android.synthetic.main.fragment_register_data.*

class RegisterDataFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterDataFragment()
    }

    interface Callback {
        fun onGetRegisterData(phoneNumber: String, email: String, password: String)
    }

    private lateinit var callback: Callback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as Callback
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement ${this}.Callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendButton.setOnClickListener {
            callback.onGetRegisterData(
                    phoneEdit.text.toString(),
                    emailEdit.text.toString(),
                    passwordEdit.text.toString()
            )
        }
    }
}