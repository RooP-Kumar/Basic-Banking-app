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
import com.example.simplebankingapplication.ListAdapter.ChooseUserAdapter
import com.example.simplebankingapplication.R
import com.example.simplebankingapplication.databinding.FragmentChooseToPayBinding

class ChooseToPayFragment : Fragment() {
    private lateinit var binding: FragmentChooseToPayBinding
    private lateinit var chooseuseradapter: ChooseUserAdapter

    private lateinit var bankViewModel: BankViewModel
    private lateinit var viewForSnackbar: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseToPayBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.chooseuserToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.choosetopaytext)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.chooseuserToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        viewForSnackbar = binding.idForSnackbar

        val currentUser = arguments?.getSerializable("user") as Users
        val dao = BankDatabase.getDatabase(requireContext()).utilityDao() // Getting Dao from database for repo
        val repo = BankRepository(dao) // Getting repository for viewmodel
        bankViewModel = ViewModelProvider(requireActivity(), BankViewModelFactory(repo)).get(BankViewModel::class.java)

        chooseuseradapter = ChooseUserAdapter(
                requireContext(),
                currentUser,
                findNavController(),
                bankViewModel,
                viewForSnackbar
        )

        binding.chooseRecyclerview.adapter = chooseuseradapter
        binding.chooseRecyclerview.layoutManager = LinearLayoutManager(requireContext())


        bankViewModel.allusers.observe(requireActivity(), {
            it?.let {
                chooseuseradapter.updateList(it, currentUser.userId-1)
            }
        })

        return binding.root
    }


}