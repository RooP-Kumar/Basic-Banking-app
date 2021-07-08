package com.example.simplebankingapplication.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Users")
class Users(
        val userName: String,
        val userEmail: String,
        val userImage: Int,
        var userAmount: Int,
        val bankAccount: String,
        val bankIFSC: String,
        val userPhone: String,
        @PrimaryKey val userId: Int
): Serializable {
}