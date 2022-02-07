package com.utechia.tdf.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.QuestionModel

class CreateSurveyViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle , val type:String,  val survey:List<QuestionModel>) : FragmentStateAdapter(fa,lifecycle) {

    override fun getItemCount(): Int = survey.size

    override fun getItemViewType(position: Int): Int = position

    override fun createFragment(position: Int): Fragment {

        return when (type) {

            SurveyEnum.Rate.survey -> {
                CreateSurveyChildFragment(survey[position], type)
            }

            SurveyEnum.Multi.survey -> {
                CreateSurveyChildFragment(survey[position], type)
            }

            SurveyEnum.Open.survey -> {
                CreateSurveyChildFragment(survey[position], type)
            }
            else -> {
                CreateSurveyChildFragment(survey[position], type)
            }
        }
    }
}