package com.utechia.tdf.home.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.main.MainActivity
import com.utechia.tdf.databinding.FragmentHomeUserBinding
import com.utechia.tdf.home.teaboy.TeaBoyHomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeUserBinding
    private lateinit var database: DatabaseReference
    private val homeViewModel: UserHomeViewModel by viewModels()
    private val userHomeAdapter: UserHomeAdapter = UserHomeAdapter()
    private lateinit var prefs: SharedPreferences

    companion object{

        const val Start = "start"
        const val ID = "userId"

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        database = Firebase.database("https://tdf-oms-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
*/
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)

        homeViewModel.getNews()

        binding.refreshLayout.setOnRefreshListener {

            homeViewModel.getNews()

        }

        if (prefs.getBoolean(Start,false)) {

            (activity as MainActivity).setupUser()

            with(prefs.edit()){

                putBoolean(Start,false)

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
                    binding.refreshLayout.isRefreshing = false
                    userHomeAdapter.addData(it.data)
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = true
                    binding.recyclerView.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = false
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}