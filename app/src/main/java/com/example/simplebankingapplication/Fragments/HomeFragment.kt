package com.example.simplebankingapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.simplebankingapplication.R
import com.example.simplebankingapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.usersBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_usersFragment)
        }

        binding.transectionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_transectionsFragment)
        }

        return binding.root
    }

}