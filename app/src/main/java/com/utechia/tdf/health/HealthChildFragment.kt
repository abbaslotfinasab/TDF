package com.utechia.tdf.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.enum.HealthEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentHealthChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthChildFragment(val health: String) : Fragment() {

    private lateinit var binding: FragmentHealthChildBinding
    private val healthViewModel:HealthViewModel by viewModels()
    private val healthAdapter :HealthAdapter = HealthAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(health){

            HealthEnum.Daily.health -> {
                binding.ChartLayout.visibility = View.GONE
                healthViewModel.getTop()
            }

            HealthEnum.Weekly.health -> {
                binding.ChartLayout.visibility = View.VISIBLE
                healthViewModel.getTop()

            }

            HealthEnum.Monthly.health -> {
                binding.ChartLayout.visibility = View.VISIBLE
                healthViewModel.getTop()

            }
        }
        binding.recyclerView.apply {
            adapter = healthAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(HealthItemDecoration())
        }


        observer()
    }

    private fun observer(){
        healthViewModel.topHealthModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    healthAdapter.addData(it.data)
                }

                is Result.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                }

                is Result.Error -> {
                    binding.refreshLayout.isRefreshing = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}