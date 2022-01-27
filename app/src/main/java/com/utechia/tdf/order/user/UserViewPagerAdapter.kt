package com.utechia.tdf.order.user

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
                UserOrderChildFragment(OrderEnum.Pending.order)
            }

            1 -> {
                UserOrderChildFragment(OrderEnum.Delivered.order)
            }

            2 -> {
                UserOrderChildFragment(OrderEnum.Cancel.order)
            }

            else -> {
                UserOrderChildFragment(OrderEnum.Pending.order)
            }
        }
    }
}