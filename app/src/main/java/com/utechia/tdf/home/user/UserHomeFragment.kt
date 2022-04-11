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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.utechia.data.entity.home.News
import com.utechia.data.entity.home.Transaction
import com.utechia.domain.enum.MainEnum
import com.utechia.tdf.R
import com.utechia.tdf.main.MainActivity
import com.utechia.tdf.databinding.FragmentHomeUserBinding
import com.utechia.tdf.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeUserBinding
    private lateinit var database: DatabaseReference
    private val mainViewModel: MainViewModel by viewModels({ requireActivity()})
    private val userHomeAdapter: UserHomeAdapter = UserHomeAdapter()
    private lateinit var prefs: SharedPreferences
    private var employeeId = ""

    companion object{

        const val Start = "start"
        const val ID = "employeeId"

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

        database = Firebase.database("https://tdf-oms-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)

        employeeId = prefs.getInt(ID, 0).toString()


        if (prefs.getBoolean(Start,false)) {

            (activity as MainActivity).setupUser()

            with(prefs.edit()){

                putBoolean(Start,false)

            }.apply()

        }

        val tranceActionListener = database

        tranceActionListener.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.prg.visibility = View.GONE
                binding.newsTitle.visibility = View.VISIBLE


                userHomeAdapter.news.clear()
                snapshot.child("News").children.forEach {
                    userHomeAdapter.addData(it.getValue<News>()!!)
                }

                val enter = snapshot.child("Transaction").child(employeeId).child("first_checkin").getValue<Transaction>()
                val exit = snapshot.child("Transaction").child(employeeId).child("last_checkout").getValue<Transaction>()

                if (enter?.punch_time.isNullOrEmpty()){
                    binding.transactionLayout.visibility = View.GONE
                }
                else{
                    binding.transactionLayout.visibility = View.VISIBLE
                    binding.entranceDate.text = enter?.punch_time
                    binding.entranceFloor.text = enter?.area_alias

                    if (exit?.punch_time.isNullOrEmpty()){
                        binding.exitDate.visibility = View.GONE
                        binding.exitFloor.visibility = View.GONE
                    }
                    else{
                        binding.exitDate.text = exit?.punch_time
                        binding.exitFloor.text = exit?.area_alias
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }
        })


        binding.recyclerView.apply {
            adapter = userHomeAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
          //  addItemDecoration(UserHomeItemDecoration())
        }

        binding.pedometerLayout.setOnClickListener {
            findNavController().navigate(R.id.action_userhomeFragment_to_healthParentFragment)
        }

        observer()
    }

    private fun observer() {

        mainViewModel.stepCounter.observe(viewLifecycleOwner){

            binding.stepsCount.text = it.toString()
            binding.calory.text = (it*0.05).toInt().toString()

        }
    }
}