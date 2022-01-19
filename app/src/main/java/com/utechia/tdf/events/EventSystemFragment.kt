package com.utechia.tdf.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.utechia.tdf.databinding.FragmentEventSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventSystemFragment : Fragment() {

    private lateinit var binding: FragmentEventSystemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                       binding.emptyLayout.visibility = View.GONE

                    }
                    else -> {
                        binding.emptyLayout.visibility = View.VISIBLE

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {


            }

            override fun onTabReselected(tab: TabLayout.Tab?) {


            }

        })

     binding.recyclerView.apply {

     }


    }
}