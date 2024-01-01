@file:OptIn(ExperimentalMaterial3Api::class)

package vn.doitsolutions.quickz.pages.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import vn.doitsolutions.quickz.model.Login
import vn.doitsolutions.quickz.model.UserResponseObject
import vn.doitsolutions.quickz.network.LoginApi
import vn.doitsolutions.quickz.pages.result.Finished
import vn.doitsolutions.quickz.ui.theme.QuickZTheme

class LoginPage : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickZTheme() {
                LoginView(
                loginSuccessCallback = {
                    var user = it?.data
                    val i = Intent(this, HomePage::class.java)
                    i.putExtra("userObject", user)
                    startActivity(i)
                }
                )
            }
        }
    }
}

@Composable
fun LoginView(
    loginViewModel: LoginViewModel = viewModel(),
    loginSuccessCallback: (UserResponseObject?) -> Unit
) {

    var loginStatus by loginViewModel.loginStatus
    var loginMessage by loginViewModel.loginMessage
    val context  = LocalContext.current

    when(val status = loginStatus){
        "loading" -> {
            loginMessage = "Process Login"
        }
        "success" -> {
            loginMessage = "Login Success"
        }
        "fail" -> {
            loginMessage = "Login Failed"
        }
    }

    if (loginStatus == "success") {
        loginSuccessCallback(loginViewModel.userResponseObject)
    }
    if (loginStatus != "init") {
        Toast.makeText(context,
            loginViewModel.loginMessage.value,
            Toast.LENGTH_LONG).show()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background((Color(0xfff9B7BF8))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.padding(64.dp)) {

            Image(
                painter = rememberAsyncImagePainter("https://cdni.iconscout.com/illustration/premium/thumb/user-rating-4118325-3414906.png"),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp),
                alignment = Alignment.Center
            )

        }


        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White, //Card background color
                contentColor = Color.Black  //Card content color,e.g.text
            )

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            ) {
                Text(
                    "Welcome back!",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Login or create an account to take quiz, take part in challenge, and more.",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 12.dp),
                )
                Text(
                    "Email",
                    modifier = Modifier.padding(top = 32.dp),
                    fontWeight = FontWeight.Bold
                )
                EmailTextField(onValueChangeCallback = { loginViewModel.username = it.toString() })
                Text(
                    "Current Password",
                    modifier = Modifier.padding(top = 32.dp),
                    fontWeight = FontWeight.Bold
                )
                PasswordTextField(onValueChangeCallback = { loginViewModel.password = it })
                //Button
                LoginButton(onClick = {
                    loginViewModel.login()
                })
            }

        }

    }
}


@Composable
fun EmailTextField(onValueChangeCallback: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
            .background(Color(0xFFFFFFFF)),
        onValueChange = {
            text = it
            onValueChangeCallback(it.text)
        }
    )
}

@Composable
fun PasswordTextField(onValueChangeCallback: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
            .background(Color(0xFFFFFFFF)),
        onValueChange = {
            text = it
            onValueChangeCallback(it.text)
        }
    )
}

@Composable
fun LoginButton(onClick: () -> Unit) {
//    F28254
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28254)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(64.dp),
        onClick = { onClick() }) {
        Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)

    }
}


@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    QuickZTheme {
        LoginView(loginSuccessCallback = {})
    }
}