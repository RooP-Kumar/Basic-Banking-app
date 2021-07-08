package com.example.simplebankingapplication.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transections(   val fromname: String,
                      val Toname: String,
                      val amount: Int,
                      val isSuccess: Boolean
                      ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}