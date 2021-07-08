package com.example.simplebankingapplication.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Users::class, Transections::class), version = 1, exportSchema = false)
abstract class BankDatabase: RoomDatabase() {
    abstract fun utilityDao(): UtilityDao

    companion object {
        private var INSTANCE: BankDatabase? = null
        fun getDatabase(context: Context): BankDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, BankDatabase::class.java,
                "Users").build()
                INSTANCE = instance
                instance
            }
        }
    }
}