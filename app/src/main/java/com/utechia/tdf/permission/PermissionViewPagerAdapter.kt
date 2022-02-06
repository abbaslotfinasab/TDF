package com.utechia.tdf.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.PermissionEnum

class PermissionViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                PermissionChildFragment(PermissionEnum.Wait.permission)
            }

            1 -> {
                PermissionChildFragment(PermissionEnum.Expired.permission)
            }

            2 -> {
                PermissionChildFragment(PermissionEnum.Cancelled.permission)
            }

            else -> {
                PermissionChildFragment(PermissionEnum.Wait.permission)
            }
        }
    }
}