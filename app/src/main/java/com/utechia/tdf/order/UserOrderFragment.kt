package com.utechia.tdf.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserOrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var userViewPagerAdapter: UserViewPagerAdapter
    private var type = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewPagerAdapter = UserViewPagerAdapter(parentFragmentManager, lifecycle)

        binding.pager.adapter = userViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString("type","")
        }

        TabLayoutMediator(binding.tabLayout,binding.pager){ tab, position ->

            when(position){

                0 ->{
                    tab.text = getText(R.string.pending)
                }

                1 ->{
                    tab.text = getText(R.string.delivered)
                }

                2 ->{
                    tab.text = getText(R.string.cancelled)
                }
            }

            when(type){

                "rate" -> {
                    binding.pager.setCurrentItem(1,true)
                }
                "cancel" ->{
                    binding.pager.setCurrentItem(0,true)

                }
                else -> {
                    binding.pager.setCurrentItem(tab.position,true)

                }

            }}.attach()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {

                    0 -> {
                        binding.pager.currentItem = tab.position

                    }
                    1 -> {
                        binding.pager.currentItem = tab.position

                    }
                    2 -> {
                        binding.pager.currentItem = tab.position
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                /*  when(tab?.position){

                    0 -> {
                        binding.pager.currentItem = tab.position

                    }
                    1 -> {
                        binding.pager.currentItem = tab.position

                    }
                    2 -> {
                        binding.pager.currentItem = tab.position

                    }
                }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                /*   when(tab?.position){

                    0 -> {
                        binding.pager.currentItem = tab.position

                    }
                    1 -> {
                        binding.pager.currentItem = tab.position

                    }
                    2 -> {
                        binding.pager.currentItem = tab.position

                    }
                }*/
            }
        })
    }
}