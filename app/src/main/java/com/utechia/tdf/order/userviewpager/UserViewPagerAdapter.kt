package com.utechia.tdf.order.userviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                UserPendingFragment()
            }

            1 -> {
                UserDeliveredFragment()
            }

            2 -> {
                UserCancelledFragment()
            }

            else -> {
                UserOrderFragment()
            }
        }
    }
}