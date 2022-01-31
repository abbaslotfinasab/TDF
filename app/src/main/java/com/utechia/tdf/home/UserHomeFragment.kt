package com.utechia.tdf.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.main.MainActivity
import com.utechia.tdf.databinding.FragmentHomeUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeUserBinding
    private val homeViewModel:UserHomeViewModel by viewModels()
    private val userHomeAdapter:UserHomeAdapter = UserHomeAdapter()
    private lateinit var prefs: SharedPreferences
    private var name = ""
    private var job = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)

        homeViewModel.getNews()

        if (prefs.getBoolean("Start",false)) {

            name = prefs.getString("name", "").toString()
            job = prefs.getString("job", "").toString()
            (activity as MainActivity).setupUser()

            with(prefs.edit()){

                putBoolean("Start",false)

            }.apply()

        }

        binding.recyclerView.apply {
            adapter = userHomeAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(UserHomeItemDecoration())
        }
        observer()
    }

    private fun observer() {
        homeViewModel.news.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    userHomeAdapter.addData(it.data)
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}