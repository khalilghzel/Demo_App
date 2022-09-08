package com.wbconcept.myapplication.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.Util.Resource
import com.wbconcept.myapplication.Entities.LoginRequest
import com.wbconcept.myapplication.Entities.Login_response
import com.wbconcept.myapplication.Repositories.SessionRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException

class SessionViewModel(
    val sessionRepository : SessionRepository
) : ViewModel() {

    var loginResponse : Login_response? = null
    val accessToken : MutableLiveData<Resource<Login_response>> = MutableLiveData()
init {

}
    fun login(login_credentials : LoginRequest) = viewModelScope.launch {
        accessToken.postValue(Resource.Loading())
        try {
            val response  = sessionRepository.login(login_credentials)
            accessToken.postValue(hundleLoginResponse(response))
        }
    catch (e : UnknownHostException){
        accessToken.postValue(e.message?.let { Resource.Error(it) })
    }
    }
    private fun hundleLoginResponse(response: Response<Login_response>):Resource<Login_response>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}