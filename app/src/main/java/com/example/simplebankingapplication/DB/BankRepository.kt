package com.example.simplebankingapplication.DB

import androidx.lifecycle.LiveData

class BankRepository(private val utilityDao: UtilityDao) {
    val allTransections: LiveData<List<Transections>> = utilityDao.transectionData()
    val allUsers: LiveData<List<Users>> = utilityDao.usersData()

    suspend fun insertTransection(transections: Transections) {
        utilityDao.insertTransection(transections)
    }

    suspend fun insertUser(user: Users) {
        utilityDao.insertUser(user)
    }

    suspend fun updateAmount(amount: Int, tid: Int){
        utilityDao.updateAmount(amount, tid)
    }

    suspend fun deleteAllUsers(){
        utilityDao.deleteAllUsers()
    }

}