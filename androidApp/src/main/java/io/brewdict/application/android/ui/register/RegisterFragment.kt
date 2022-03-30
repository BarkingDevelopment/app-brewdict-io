package io.brewdict.application.android.ui.register

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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import io.brewdict.application.android.databinding.FragmentRegisterBinding
import io.brewdict.application.api_consumption.models.LoggedInUser

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        ).apply {
            composeView.setContent {
                MaterialTheme {
                    Layout()
                }
            }
        }

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory()).get(RegisterViewModel::class.java)

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

        registerViewModel.registrationResult.observe(viewLifecycleOwner,
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

        //TODO: Perform successful login sequence

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        startActivity(Intent(activity, DashboardActivity::class.java))
    }

    private fun fail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    // Jetpack Compose UI
    // TODO: Align colours.

    @Composable
    fun RegisterForm(){
        val username = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val retypePassword = remember { mutableStateOf(TextFieldValue()) }

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
                        placeholder = {
                            Text(
                                text = getString(R.string.prompt_username),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = username.value,
                        onValueChange = { username.value = it }
                    )

                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_email_24),
                                contentDescription = null,// decorative element
                            )
                        },
                        placeholder = {
                            Text(
                                text = getString(R.string.prompt_email),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = email.value,
                        onValueChange = { email.value = it }
                    )

                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_vpn_key_24),
                                contentDescription = null,// decorative element
                            )
                        },
                        placeholder = {
                            Text(
                                text = getString(R.string.prompt_password),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = password.value,
                        onValueChange = { password.value = it }
                    )

                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_vpn_key_24),
                                contentDescription = null,// decorative element
                            )
                        },
                        placeholder = {
                            Text(
                                text = getString(R.string.prompt_retype_password),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = retypePassword.value,
                        onValueChange = { retypePassword.value = it }
                    )

                    OutlinedButton(
                        // TODO: Add button functionality.
                        onClick = {
                            if (password.value.text == retypePassword.value.text){
                                registerViewModel.register(
                                    username = username.value.text,
                                    email = email.value.text,
                                    password = password.value.text
                                )
                            }
                        }
                    ) {
                        Text(getString(R.string.action_register))
                    }
                }
            }
        }
    }

    @Composable
    fun Layout(){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            RegisterForm()
        }
    }

    // Previews
    @Preview
    @Composable
    fun RegisterFragmentFormPreview(){
        RegisterForm()
    }

    @Preview
    @Composable
    fun LayoutPreview(){

    }
}