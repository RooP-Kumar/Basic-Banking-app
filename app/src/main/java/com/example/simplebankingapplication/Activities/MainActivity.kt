package com.example.simplebankingapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.simplebankingapplication.DB.*
import com.example.simplebankingapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var bankViewModel: BankViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val dao = BankDatabase.getDatabase(this).utilityDao()
        val repo = BankRepository(dao)
        bankViewModel = ViewModelProvider(this, BankViewModelFactory(repo)).get(BankViewModel::class.java)

    }

    override fun onStart() {
        val user1 = Users("John Carter", "john@oulook.com", R.drawable.john,1500, "34151001", "ifsc001", "9063514512",1)
        val user2 = Users("Harry", "harry@oulook.com", R.drawable.harry,2500, "34151001", "ifsc001", "9063514512", 2)
        val user3 = Users("Henry", "henry@oulook.com", R.drawable.henry,1750, "34151001", "ifsc001", "9063514512", 3)
        val user4 = Users("Oliver", "oliver@oulook.com", R.drawable.oliver,3500, "34151001", "ifsc001", "9063514512", 4)
        val user5 = Users("Elijah", "elijah@oulook.com", R.drawable.elijah,4600, "34151001", "ifsc001", "9063514512", 5)
        val user6 = Users("James", "james@oulook.com", R.drawable.james,15000, "34151001", "ifsc001", "9063514512", 6)
        val user7 = Users("Lana Williams", "lana30@gmail.com", R.drawable.lana,6820, "34151001", "ifsc001", "9063514512", 7)
        val user8 = Users("Robert", "robert2@oulook.com", R.drawable.robert,3520, "34151001", "ifsc001", "9063514512", 8)
        val user9 = Users("Alexander", "alex321@oulook.com", R.drawable.alexander,1532, "34151001", "ifsc001", "9063514512", 9)
        val user10 = Users("Liam", "liam@oulook.com", R.drawable.liam,15259, "34151001", "ifsc001", "9063514512", 10)

        bankViewModel.insertUser(user1)
        bankViewModel.insertUser(user2)
        bankViewModel.insertUser(user3)
        bankViewModel.insertUser(user4)
        bankViewModel.insertUser(user5)
        bankViewModel.insertUser(user6)
        bankViewModel.insertUser(user7)
        bankViewModel.insertUser(user8)
        bankViewModel.insertUser(user9)
        bankViewModel.insertUser(user10)
        super.onStart()
    }

}