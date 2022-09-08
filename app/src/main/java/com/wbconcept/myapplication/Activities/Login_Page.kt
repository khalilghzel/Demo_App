package com.wbconcept.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.Util.Resource
import com.wbconcept.myapplication.Api.SessionManager
import com.wbconcept.myapplication.Entities.LoginRequest
import com.wbconcept.myapplication.R
import com.wbconcept.myapplication.Repositories.SessionRepository
import com.wbconcept.myapplication.ViewModels.SessionViewModel
import com.wbconcept.myapplication.ViewModels.SessionViewModelProviderFacory
import kotlinx.android.synthetic.main.login.*


class Login_Page : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    lateinit var viewModel: SessionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val sessionRepository = SessionRepository()
        sessionManager = SessionManager(this)

        val viewModelProviderFacory = SessionViewModelProviderFacory(sessionRepository)

        viewModel =
            ViewModelProvider(this, viewModelProviderFacory).get(SessionViewModel::class.java)



        loginBtn.setOnClickListener(View.OnClickListener {
            if (checkCredentials()) {
                login_api()
            }
        })

    }

    private fun login_api() {

        viewModel.login(
            LoginRequest(
                email = emailEt.text.toString(),
                password = passwordEt.text.toString()
            )
        )
        viewModel.accessToken?.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgBar()
                    response.data?.let { newsresponse ->
                        sessionManager.saveAuthToken(newsresponse.authToken)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                is Resource.Loading -> {
                    showProgBar()
                }
                is Resource.Error -> {
                    hideProgBar()
                    response.message?.let { message ->
                        Log.e("TAG", message)
                        Toast.makeText(this,"Check credentials", Toast.LENGTH_LONG).show()

                    }
                }
            }

        })


    }


    private fun hideProgBar() {
        progress.visibility = View.GONE
        loginBtn.visibility = View.VISIBLE

    }

    private fun showProgBar() {
        progress.visibility = View.VISIBLE
        loginBtn.visibility = View.GONE

    }


    private fun checkCredentials(): Boolean {
        var result = true
        if (emailEt.text.toString().equals("")) {
            result = false
            val animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.shake)
            emailEt.startAnimation(animationFadeIn)

        }

        if (passwordEt.text.toString().equals("")) {
            result = false
            val animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.shake)
            passwordEt.startAnimation(animationFadeIn)

        }


        return result
    }
}
