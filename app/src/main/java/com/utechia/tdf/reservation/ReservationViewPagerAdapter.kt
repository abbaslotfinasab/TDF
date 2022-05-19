package com.utechia.tdf.reservation

import com.utechia.tdf.survey.SurveyChildFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.SurveyEnum

class ReservationViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                ReservationChildFragment(SurveyEnum.Evaluate.survey)
            }

            1 -> {
                ReservationChildFragment(SurveyEnum.Expired.survey)
            }

            else -> {
                ReservationChildFragment(SurveyEnum.Evaluate.survey)
            }
        }
    }
}