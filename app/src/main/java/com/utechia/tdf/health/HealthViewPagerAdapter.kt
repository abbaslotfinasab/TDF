package com.utechia.tdf.health

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.HealthEnum

class HealthViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                HealthChildFragment(HealthEnum.Daily.health)
            }

            1 -> {
                HealthChildFragment(HealthEnum.Weekly.health)
            }

            2 -> {
                HealthChildFragment(HealthEnum.Monthly.health)
            }

            else -> {
                HealthChildFragment(HealthEnum.Daily.health)
            }
        }
    }
}