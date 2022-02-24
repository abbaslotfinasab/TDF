package com.utechia.tdf.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.EventsEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentEventSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventSystemFragment : Fragment() {

    private lateinit var binding: FragmentEventSystemBinding
    private lateinit var eventsViewPagerAdapter : EventsViewPagerAdapter
    private var type = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsViewPagerAdapter = EventsViewPagerAdapter(childFragmentManager,lifecycle)
        binding.pager.adapter = eventsViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(EventsEnum.Type.event,"")
        }

        TabLayoutMediator(binding.tabLayout,binding.pager){ tab, position ->

            when(position){

                0 ->{
                    tab.text = getText(R.string.past_events)
                }

                1 ->{
                    tab.text = getText(R.string.current_events)
                }

                2 ->{
                    tab.text = getText(R.string.upcoming_events)
                }
            }

            when(type){

                EventsEnum.End.event-> {

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(0,true)
                    },300)

                }
                EventsEnum.Current.event ->{

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(1,true)
                    },300)
                }

                EventsEnum.Upcoming.event->{

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(2,true)
                    },300)
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

            /*    when (tab?.position) {

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

               /* when (tab?.position) {

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