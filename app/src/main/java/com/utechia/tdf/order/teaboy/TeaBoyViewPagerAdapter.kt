package com.utechia.tdf.order.teaboy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.OrderEnum

class TeaBoyViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                TeaBoyOrderChildFragment()
            }

            1 -> {
                TeaBoyOrderChildFragment(OrderEnum.Delivered.order)
            }

            2 -> {
                TeaBoyOrderChildFragment(OrderEnum.Cancel.order)
            }

            else -> {
                TeaBoyOrderChildFragment()
            }
        }
    }
}