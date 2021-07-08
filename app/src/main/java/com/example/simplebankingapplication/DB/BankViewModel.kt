package com.example.simplebankingapplication.DB

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class BankViewModel(private val repository: BankRepository): ViewModel() {
    val alltransections: LiveData<List<Transections>> = repository.allTransections
    val allusers: LiveData<List<Users>> = repository.allUsers

    fun insertUser(users: Users) = viewModelScope.launch {
        repository.insertUser(users)
    }

    fun updateAmount(amount: Int, tid: Int) = viewModelScope.launch {
        repository.updateAmount(amount, tid)
    }

    fun insertTransection(transection: Transections) = viewModelScope.launch {
        repository.insertTransection(transection)
    }

    fun deleteAllUsers() = viewModelScope.launch {
        repository.deleteAllUsers()
    }

}

class BankViewModelFactory(private val repository: BankRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BankViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BankViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}