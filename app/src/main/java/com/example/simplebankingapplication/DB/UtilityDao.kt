package com.example.simplebankingapplication.DB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class UtilityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertTransection(data: Transections)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertUser(data: Users)

    @Query("DELETE FROM users")
    abstract suspend fun deleteAllUsers()

    @Query("select * from transections order by id")
    abstract fun transectionData() : LiveData<List<Transections>>

    @Query("select * from users order by userId")
    abstract fun usersData() : LiveData<List<Users>>

    @Query("UPDATE Users SET userAmount = :amount where userId = :tid")
    abstract suspend fun updateAmount(amount:Int, tid:Int)
}