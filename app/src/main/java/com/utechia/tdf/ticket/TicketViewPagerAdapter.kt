package com.utechia.tdf.ticket

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.enum.TicketEnum

class TicketViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                TicketChildFragment(TicketEnum.Open.ticket)
            }

            1 -> {
                TicketChildFragment(TicketEnum.Close.ticket)
            }

            else -> {
                TicketChildFragment(TicketEnum.Open.ticket)
            }
        }
    }
}