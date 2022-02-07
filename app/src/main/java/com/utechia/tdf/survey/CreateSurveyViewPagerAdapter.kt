package com.utechia.tdf.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.QuestionModel

class CreateSurveyViewPagerAdapter(fa: FragmentManager, lifecycle:Lifecycle , val type:String) : FragmentStateAdapter(fa,lifecycle) {

    private val survey:MutableList<QuestionModel> = mutableListOf()

    fun addData(_survey: List<QuestionModel>){
        survey.clear()
        notifyDataSetChanged()
        survey.addAll(_survey)
        notifyItemRangeChanged(0,_survey.size)
    }


    override fun getItemCount(): Int = survey.size

    override fun createFragment(position: Int): Fragment {

        return when (type) {

            SurveyEnum.Rate.survey -> {
                CreateSurveyChildFragment(survey[position],type)
            }

            SurveyEnum.Multi.survey -> {
                CreateSurveyChildFragment(survey[position],type)
            }

            SurveyEnum.Open.survey -> {
                CreateSurveyChildFragment(survey[position],type)
            }

            else -> {
                CreateSurveyChildFragment(survey[position],type)
            }
        }
    }
}