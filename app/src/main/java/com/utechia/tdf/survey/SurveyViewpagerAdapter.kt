package com.utechia.tdf.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.PermissionEnum
import com.utechia.domain.enum.SurveyEnum

class SurveyViewpagerAdapter(fa: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                SurveyChildFragment(SurveyEnum.Evaluate.survey)
            }

            1 -> {
                SurveyChildFragment(SurveyEnum.Expired.survey)
            }

            else -> {
                SurveyChildFragment(SurveyEnum.Evaluate.survey)
            }
        }
    }
}