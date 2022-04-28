package io.brewdict.application.android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.brewdict.application.android.MainActivity
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentProfileBinding
import io.brewdict.application.android.models.UserPreviewParameterProvider
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.apis.brewdict.BrewdictAPI

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private lateinit var viewModel: ProfileViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
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
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.passwordResetResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    resetSuccess()
                }

                result.error?.let {
                    fail(it)
                }
            }
        )

        viewModel.logoutResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    logoutSuccess()
                }

                result.error?.let {
                    fail(it)
                }
            }
        )
    }

    private fun resetSuccess() {

    }

    private fun logoutSuccess() {
        val welcome = "Logout Successful"

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        startActivity(Intent(activity, MainActivity::class.java))
    }


    private fun fail(@StringRes errorString: Int) {
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
    fun ProfileAvatar(user: User){
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = null, // decorative element
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape) // clip to the circle shape
                    .border(2.dp, Color.White, RoundedCornerShape(10))
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                contentDescription = null ,// decorative element
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape) // clip to the circle shape
                    .background(Color.White)
                    .padding(2.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }

    @Composable
    fun ProfileInlineCard(user: User, modifier: Modifier?){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier ?: Modifier
        ) {
            ProfileAvatar(user)
            Column (
                modifier = Modifier
                    .padding(16.dp, 0.dp)
            ) {
                Text (
                    text = "@" + user.username,
                    fontSize = 24.sp,
                )
            }
        }
    }

    @Composable
    fun ProfileCard(user: User, modifier: Modifier?){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier ?: Modifier
        ) {
            ProfileAvatar(user)
            Text(
                text = "@" + user.username,
                fontSize = 24.sp,
            )
        }
    }

    @Composable
    fun ResetPasswordForm(){
        val newPassword = remember { mutableStateOf(TextFieldValue()) }
        val retypeNewPassword = remember { mutableStateOf(TextFieldValue()) }

        Column{
            Row(
                modifier = Modifier
                    .padding(0.dp, 10.dp)
            ){
                Text(
                    text = "Reset Password",
                    fontSize = 18.sp,
                )
            }

            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_vpn_key_24),
                                contentDescription = null,// decorative element
                            )
                        },
                        placeholder = {
                            Text(
                                text = "New Password", style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = newPassword.value,
                        onValueChange = { newPassword.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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
                                text = "Re-type New Password", style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = retypeNewPassword.value,
                        onValueChange = { retypeNewPassword.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    OutlinedButton(
                        // TODO: Add button functionality.
                        onClick = { viewModel.resetPassword(newPassword.value.text, retypeNewPassword.value.text) }) {
                        Text("Reset Password")
                    }

                    Button(
                        // TODO: Add button functionality.
                        onClick = { viewModel.logout() }) {
                        Text("Logout")
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
            BrewdictAPI.loggedInUser?.user?.let { ProfileInlineCard(it, null) }

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .padding(0.dp, 24.dp)
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            ResetPasswordForm()
        }
    }

    // Previews
    @Preview
    @Composable
    fun ProfileInlineCardPreview(
        @PreviewParameter(UserPreviewParameterProvider::class) user: User
    ){
        ProfileInlineCard(user = user, null)
    }

    @Preview
    @Composable
    fun ProfileCardPreview(
        @PreviewParameter(UserPreviewParameterProvider::class) user: User
    ){
        ProfileCard(user = user, null)
    }

    @Preview
    @Composable
    fun ResetPasswordFormPreview(){
        ResetPasswordForm()
    }

    @Preview
    @Composable
    fun LayoutProfilePreview(
        @PreviewParameter(UserPreviewParameterProvider::class) user: User
    ){
        Content()
    }
}