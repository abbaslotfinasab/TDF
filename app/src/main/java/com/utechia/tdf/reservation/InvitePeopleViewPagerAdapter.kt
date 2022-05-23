package com.utechia.tdf.reservation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class InvitePeopleViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                SearchPeopleFragment()
            }

            1 -> {
                AddGuestFragment()
            }

            else -> {
                SearchPeopleFragment()
            }
        }
    }
}