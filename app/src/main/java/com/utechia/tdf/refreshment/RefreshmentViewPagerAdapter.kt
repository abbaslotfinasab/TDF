package com.utechia.tdf.refreshment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.RefreshmentEnum

class RefreshmentViewPagerAdapter(private val fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                CreateRefreshmentChildFragment(RefreshmentEnum.Food.refreshment)
            }

            1 -> {
                CreateRefreshmentChildFragment(RefreshmentEnum.Hot.refreshment)
            }

            2 -> {
                CreateRefreshmentChildFragment(RefreshmentEnum.Cold.refreshment)
            }

            else -> {
                CreateRefreshmentChildFragment(RefreshmentEnum.Food.refreshment)
            }
        }
    }
}