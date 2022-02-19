package io.brewdict.application.android.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentLoginBinding
import io.brewdict.application.android.models.LoggedInUser

class LoginFragment : Fragment() {
    lateinit var txt_user: EditText
    lateinit var txt_password: EditText
    lateinit var btn_login: Button
    lateinit var bar_loading: ProgressBar

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)

        txt_user = binding.username
        txt_password = binding.password
        btn_login = binding.login
        bar_loading = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
        Observer { loginFormState ->
            if (loginFormState == null) {
                return@Observer
            }

            btn_login.isEnabled = loginFormState.isDataValid
            loginFormState.usernameError?.let {
                txt_user.error = getString(it)
            }

            loginFormState.passwordError?.let {
                txt_password.error = getString(it)
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
        Observer { loginResult ->
            loginResult ?: return@Observer
            bar_loading.visibility = View.GONE

            loginResult.success?.let{
                loginSuccess(it)
            }

            loginResult.error?.let {
                loginFail(it)
            }
        })

        val afterTextChangedListener = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    identity = txt_user.text.toString(),
                    password = txt_password.text.toString()
                )
            }
        }

        txt_user.addTextChangedListener(afterTextChangedListener)
        txt_password.addTextChangedListener(afterTextChangedListener)
        txt_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                loginViewModel.login(
                    identity = txt_user.text.toString(),
                    password = txt_password.text.toString()
                )
            }
            false
        }

        btn_login.setOnClickListener{
            bar_loading.visibility = View.VISIBLE
            loginViewModel.login(
                identity = txt_user.text.toString(),
                password = txt_password.text.toString()
            )
        }
    }

    private fun loginSuccess(model: LoggedInUser) {
        val welcome = getString(R.string.welcome) + model.username

        //TODO: Perform successful login sequence

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun loginFail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}