package com.utechia.tdf.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.domain.enum.HealthEnum
import com.utechia.tdf.databinding.FragmentHealthChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthChildFragment(val health: String) : Fragment() {

    private lateinit var binding: FragmentHealthChildBinding



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
            }

            HealthEnum.Weekly.health -> {
                binding.ChartLayout.visibility = View.VISIBLE

            }

            HealthEnum.Monthly.health -> {
                binding.ChartLayout.visibility = View.VISIBLE

            }
        }

    }
}