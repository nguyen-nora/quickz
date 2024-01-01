package vn.doitsolutions.quickz.pages.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import vn.doitsolutions.quickz.model.Login
import vn.doitsolutions.quickz.model.LoginData
import vn.doitsolutions.quickz.model.UserResponseObject
import vn.doitsolutions.quickz.network.LoginApi

class LoginViewModel : ViewModel() {

    var username: String = ""
    var password: String = ""

    //    var loginStatus: String = "init"
    val loginStatus = mutableStateOf<String>("init")
    val loginMessage = mutableStateOf<String>("")
    var userResponseObject : UserResponseObject? = null

    init {
    }

    suspend fun requestLogin() = flow{
        try {
            emit("loading")
            userResponseObject =
                LoginApi.retrofitService.login(
                    Login(
                        route = "login",
                        data = LoginData(username = username, password = password)
                    )
                )
            println("Cera: " + userResponseObject?.data?.username)
            if(userResponseObject?.data?.username != null) {
                emit("success")
            }else{
                emit("fail")
            }
            //emit("success")
        } catch (e: Exception) {
            println("CERA: " + e.message)
            emit("fail")
        }


    }

    fun login() {
        viewModelScope.launch {
            loginMessage.value = "Đang đăng nhập"
            requestLogin().collect{response  -> loginStatus.value = response}
            if(loginStatus.value == "success"){
                loginMessage.value = "Đăng nhập thành công"
            }else{
                loginMessage.value = "Đăng nhập thất bại"
            }
        }
    }
}