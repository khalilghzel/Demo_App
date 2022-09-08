package com.wbconcept.myapplication.Repositories

import com.wbconcept.myapplication.Api.ApiClient
import com.wbconcept.myapplication.Entities.LoginRequest


class SessionRepository(
) {
    suspend fun login(credential : LoginRequest)=
        ApiClient.api.login(credential)



}