package com.example.simplebankingapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplebankingapplication.DB.BankDatabase
import com.example.simplebankingapplication.DB.BankRepository
import com.example.simplebankingapplication.DB.BankViewModel
import com.example.simplebankingapplication.DB.BankViewModelFactory
import com.example.simplebankingapplication.ListAdapter.TransectionAdapter
import com.example.simplebankingapplication.R
import com.example.simplebankingapplication.databinding.FragmentTransectionsBinding

class TransectionsFragment : Fragment() {

    private lateinit var binding: FragmentTransectionsBinding

    private lateinit var bankViewModel: BankViewModel

    private lateinit var transectionAdapter: TransectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransectionsBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.transectionToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.transectiosText)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.transectionToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val dao = BankDatabase.getDatabase(requireContext()).utilityDao()
        val repo = BankRepository(dao)
        bankViewModel = ViewModelProvider(requireActivity(), BankViewModelFactory(repo)).get(BankViewModel::class.java)

        transectionAdapter = TransectionAdapter(requireActivity())
        binding.transectionRecyclerView.adapter = transectionAdapter
        binding.transectionRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        bankViewModel.alltransections.observe(requireActivity(), {
            it?.let {
                if (it.size != 0) {
                    binding.noTransectionTextView.visibility = View.GONE
                    transectionAdapter.updateTransectionList(it)
                } else {
                    binding.noTransectionTextView.visibility = View.VISIBLE
                }
            }
        })

        return binding.root
    }

}