package com.wbconcept.myapplication.Entities

import com.google.gson.annotations.SerializedName

data  class Login_response  (
    @SerializedName("message")
    var message: String,

    @SerializedName("token")
    var authToken: String,

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("errors")
    var errors: String,
    @SerializedName("dateExpiration")
    var dateExpiration: String,

)

//    @SerializedName("message")
//    @Expose
//    private String message;
//    @SerializedName("token")
//    @Expose
//    private String token;
//    @SerializedName("isSuccess")
//    @Expose
//    private Boolean isSuccess;
//    @SerializedName("errors")
//    @Expose
//    private String errors;
//    @SerializedName("dateExpiration")
//    @Expose
//    private String dateExpiration;
//    @SerializedName("email")
//    @Expose
//    private String email="";
//    @SerializedName("given_name")
//    @Expose
//    private String given_name="";
//    @SerializedName("nameid")
//    @Expose
//    private String nameid="";
//    @SerializedName("errorCode")
//    @Expose