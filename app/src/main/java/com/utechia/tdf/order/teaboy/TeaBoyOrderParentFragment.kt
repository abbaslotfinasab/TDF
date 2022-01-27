package com.utechia.tdf.order.teaboy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.OrderEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTeaBoyOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyOrderParentFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyOrdersBinding
    private lateinit var teaBoyViewPagerAdapter:TeaBoyViewPagerAdapter
    private var type = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teaBoyViewPagerAdapter = TeaBoyViewPagerAdapter(parentFragmentManager, lifecycle)

        binding.pager.adapter = teaBoyViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(OrderEnum.Type.order,"")
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

                OrderEnum.Delivered.order -> {
                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(1,true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(1))

                    },100)

                }
                OrderEnum.Pending.order ->{

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(0,true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(0))

                    },100)
                }

                OrderEnum.Cancel.order ->{

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(2,true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(2))

                    },100)
                }

                else -> {
                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(0,true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(0))

                    },100)
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