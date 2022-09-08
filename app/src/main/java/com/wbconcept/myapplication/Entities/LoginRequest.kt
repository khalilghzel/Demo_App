package com.wbconcept.myapplication.Entities

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("Username")
    var email: String,

    @SerializedName("Password")
    var password: String
)