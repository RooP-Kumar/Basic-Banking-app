package com.example.simplebankingapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.simplebankingapplication.DB.Users
import com.example.simplebankingapplication.R
import com.example.simplebankingapplication.databinding.FragmentUsersDetailBinding

class UsersDetailFragment : Fragment() {

    private lateinit var binding: FragmentUsersDetailBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersDetailBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.userDetailsToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.userDetailsToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        handleEveryThing()

        return binding.root
    }

    private fun handleEveryThing() {
        val user = arguments?.getSerializable("user") as Users
        (requireActivity() as AppCompatActivity).supportActionBar?.title = user.userName

        binding.userDetailFragName.text = "Name : ${user.userName}"
        binding.userDetailFragEmail.text = "Email : ${user.userEmail}"
        binding.detailsFragAccount.text = "Account : ${user.bankAccount}"
        binding.detailsIfsc.text = "IFSC : ${user.bankIFSC}"
        binding.detailsFragTotalAmount.text = "Amount : $${user.userAmount}"
        binding.userDetailFragPhone.text = "Phone : ${user.userPhone}"
        Glide.with(this).load(user.userImage).into(binding.userDetailFragImage)

        setAnimation()

        binding.sendMoney.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", user)
            findNavController().navigate(R.id.action_usersDetailFragment_to_chooseToPayFragment, bundle)
        }
    }

    private fun setAnimation() {
        binding.userDetailFragName.translationX = 700f
        binding.userDetailFragEmail.translationX = 700f
        binding.detailsFragAccount.translationX = 700f
        binding.detailsIfsc.translationX = 700f
        binding.detailsFragTotalAmount.translationX = 700f
        binding.userDetailFragPhone.translationX = 700f

        // Animation Order
        binding.detailsFragTotalAmount.animate().translationX(0f)
                .setDuration(500).setStartDelay(0).start()                  // First
        binding.userDetailFragName.animate().translationX(0f)
                .setDuration(500).setStartDelay(100).start()                // Second
        binding.userDetailFragEmail.animate().translationX(0f)
                .setDuration(500).setStartDelay(200).start()                // Third
        binding.userDetailFragPhone.animate().translationX(0f)
                .setDuration(500).setStartDelay(300).start()                // Fourth
        binding.detailsFragAccount.animate().translationX(0f)
                .setDuration(500).setStartDelay(400).start()                // Fifth
        binding.detailsIfsc.animate().translationX(0f)
                .setDuration(500).setStartDelay(500).start()                // Sixth
    }

}