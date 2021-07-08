package com.example.simplebankingapplication.ListAdapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplebankingapplication.DB.Users
import com.example.simplebankingapplication.R


class UsersAdapter(private val navController: NavController, private val context: Context, private val users: ArrayList<Users>): RecyclerView.Adapter<UsersAdapter.MyHolder>() {

    private var lastPosition = -1

    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userName = itemView.findViewById<TextView>(R.id.userName)
        val userEmail = itemView.findViewById<TextView>(R.id.userEmail)
        val userAccount = itemView.findViewById<TextView>(R.id.userAccount)
        val userImage = itemView.findViewById<ImageView>(R.id.userImage)
        val totalAmount = itemView.findViewById<TextView>(R.id.totalAmount)
        val showDetails = itemView.findViewById<CardView>(R.id.showDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_recycler_layout, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.userName.text = users[position].userName
        holder.userEmail.text = users[position].userEmail
        holder.userAccount.text = users[position].bankAccount
        holder.totalAmount.text = "$${users[position].userAmount}"

        Glide.with(context).load(users[position].userImage).into(holder.userImage)

        if (position > lastPosition) {
            holder.showDetails.translationX = 700f
            holder.showDetails.animate().translationX(0f).setDuration(500).setStartDelay((100 + (position * 50)).toLong()).start()
            lastPosition = position
        }

        holder.showDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", users[position])
            navController.navigate(R.id.action_usersFragment_to_usersDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun updateList(newUsers: List<Users>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

}