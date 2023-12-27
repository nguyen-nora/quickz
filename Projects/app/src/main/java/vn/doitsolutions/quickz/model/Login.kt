package vn.doitsolutions.quickz.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

//class Login (
//    @SerializedName("username") var username: String,
//    @SerializedName("password") var password: String,)


data class Login (
    @Json( name = "route") var route: String = "login",
    @Json( name = "data") var data: LoginData,
    )

data class LoginData(
    @Json(name= "username") var username: String,
    @Json(name = "password") var password: String,

    )