package com.wbconcept.myapplication.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
 import com.wbconcept.myapplication.Repositories.SessionRepository

class SessionViewModelProviderFacory (val newsRepository: SessionRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SessionViewModel(newsRepository) as T
    }
}