package com.utechia.tdf.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.EventsEnum
import com.utechia.domain.enum.TicketEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTicketSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketSystemFragment : Fragment() {

    private lateinit var binding: FragmentTicketSystemBinding
    private lateinit var ticketViewPagerAdapter:TicketViewPagerAdapter
    private var type = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ticketViewPagerAdapter = TicketViewPagerAdapter(childFragmentManager,lifecycle)
        binding.pager.adapter = ticketViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(TicketEnum.Type.ticket,"")
        }


        TabLayoutMediator(binding.tabLayout,binding.pager){ tab, position ->

            when(position){

                0 ->{
                    tab.text = getText(R.string.open_tickets)
                }

                1 ->{
                    tab.text = getText(R.string.closed_tickets)
                }

            }

            when(type){

                TicketEnum.Open.ticket ->{

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(0,true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(0))

                    },200)
                }

                TicketEnum.Close.ticket ->{

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(1,true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(1))

                    },200)
                }

            }}.attach()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        binding.pager.currentItem = tab.position

                    }

                    1 -> {
                        binding.pager.currentItem = tab.position

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

               /* when(tab?.position){

                     0 -> {
                        binding.pager.currentItem = tab.position

                    }

                    1 -> {
                        binding.pager.currentItem = tab.position

                    }
                }*/

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                /* when(tab?.position){

                     0 -> {
                        binding.pager.currentItem = tab.position

                    }

                    1 -> {
                        binding.pager.currentItem = tab.position

                    }
               }*/

            }

        })
    }
}