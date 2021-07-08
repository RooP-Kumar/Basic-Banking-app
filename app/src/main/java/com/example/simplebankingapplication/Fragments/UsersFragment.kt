package com.example.simplebankingapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplebankingapplication.DB.*
import com.example.simplebankingapplication.ListAdapter.UsersAdapter
import com.example.simplebankingapplication.R
import com.example.simplebankingapplication.databinding.FragmentUsersBinding

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding
    private var users = ArrayList<Users>()
    private lateinit var userAdapter: UsersAdapter

    private lateinit var bankViewModel: BankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.userToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.allUserText)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.userToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        handleEveryThing() // Function to handle all task

        return binding.root
    }

    private fun handleEveryThing(){
        userAdapter = UsersAdapter(findNavController(), this.requireContext(), users) // initializing userAdapter
        binding.userRecyclerView.adapter = userAdapter // setting userAdapter to recyclerview adapter
        binding.userRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val datadao = BankDatabase.getDatabase(requireContext()).utilityDao()
        val repo = BankRepository(datadao)
        bankViewModel = ViewModelProvider(this, BankViewModelFactory(repo)).get(BankViewModel::class.java)

        bankViewModel.allusers.observe(requireActivity(), {
            it?.let {
                userAdapter.updateList(it)
            }
        })

    }

}