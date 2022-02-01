package com.utechia.tdf.events

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.EventsEnum
import com.utechia.domain.enum.OrderEnum

class EventsViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                EventSystemChildFragment(EventsEnum.Past.event)
            }

            1 -> {
                EventSystemChildFragment(EventsEnum.Current.event)
            }

            2 -> {
                EventSystemChildFragment(EventsEnum.Upcoming.event)
            }

            else -> {
                EventSystemChildFragment(EventsEnum.Past.event)
            }
        }
    }
}