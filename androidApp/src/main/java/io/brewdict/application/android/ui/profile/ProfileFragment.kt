package io.brewdict.application.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentProfileBinding
import io.brewdict.application.android.models.UserPreviewParameterProvider
import io.brewdict.application.android.ui.recipes.ProfileViewModel
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.apis.brewdict.BrewdictAPI

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
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
                        onValueChange = { newPassword.value = it }
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
                        onValueChange = { retypeNewPassword.value = it }
                    )

                    OutlinedButton(
                        // TODO: Add button functionality.
                        onClick = { /* Do something! */ }) {
                        Text("Reset Password")
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
        Layout()
    }
}