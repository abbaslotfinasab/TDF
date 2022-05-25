package com.utechia.tdf.reservation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.ReservationEnum

class ReservationViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                ReservationChildFragment(ReservationEnum.Invited.reservation)
            }

            1 -> {
                ReservationChildFragment(ReservationEnum.None.reservation)
            }

            else -> {
                ReservationChildFragment(ReservationEnum.Invited.reservation)
            }
        }
    }
}