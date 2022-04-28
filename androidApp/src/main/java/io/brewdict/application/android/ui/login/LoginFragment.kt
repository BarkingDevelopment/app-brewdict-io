package io.brewdict.application.android.ui.login

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.brewdict.application.android.DashboardActivity
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentLoginBinding
import io.brewdict.application.api_consumption.models.LoggedInUser

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).apply {
            composeView.setContent {
                MaterialTheme {
                    Content()
                }
            }
        }

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)

        /*
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
         */

        loginViewModel.loginResult.observe(viewLifecycleOwner,
        Observer { result ->
            result ?: return@Observer

            result.success?.let{
                success(it)
            }

            result.error?.let {
                fail(it)
            }
        })

        /*
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
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    identity = txt_user.text.toString(),
                    password = txt_password.text.toString()
                )
            }
            false
        }
         */
    }

    private fun success(model: LoggedInUser) {
        val welcome = getString(R.string.welcome) + model.user.username

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        startActivity(Intent(activity, DashboardActivity::class.java))
    }

    private fun fail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Jetpack Compose UI
    // TODO: Align colours.

    @Composable
    fun LoginForm(){
        val identity = remember { mutableStateOf(TextFieldValue("")) }
        val password = remember { mutableStateOf(TextFieldValue("")) }

        Column (
        ){
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_person_24),
                                contentDescription = null,// decorative element
                            )
                        },
                        label = { Text("Enter Identity") },
                        placeholder = {
                            Text(
                                text = getString(R.string.prompt_identity),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        singleLine = true,
                        value = identity.value,
                        onValueChange = { identity.value = it }
                    )

                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_vpn_key_24),
                                contentDescription = null,// decorative element
                            )
                        },
                        label = { Text("Enter password") },
                        placeholder = {
                            Text(
                                text = getString(R.string.prompt_password),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        singleLine = true,
                        value = password.value,
                        onValueChange = { password.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    OutlinedButton(
                        // TODO: Add button functionality.
                        onClick = {
                            loginViewModel.login(
                                identity = identity.value.text,
                                password = password.value.text
                            )
                        }
                    ) {
                        Text(getString(R.string.action_login))
                    }
                }
            }
        }
    }

    @Composable
    fun Content(){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LoginForm()
        }
    }

    // Previews
    @Preview
    @Composable
    fun LoginFormPreview(){
        LoginForm()
    }

    @Preview
    @Composable
    fun LayoutPreview(){
        Content()
    }

}