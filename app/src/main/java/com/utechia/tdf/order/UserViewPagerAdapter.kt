package com.utechia.tdf.order

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.OrderEnum

class UserViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                UserPendingFragment(OrderEnum.Pending.order)
            }

            1 -> {
                UserPendingFragment(OrderEnum.Delivered.order)
            }

            2 -> {
                UserPendingFragment(OrderEnum.Cancel.order)
            }

            else -> {
                UserPendingFragment(OrderEnum.Pending.order)
            }
        }
    }
}