package com.utechia.tdf.survey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.survey.SurveyModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SurveyAdapter(private val status:String): PagingDataAdapter<SurveyModel, SurveyAdapter.MyViewHolder>(
    DiffUtilCallBack()
){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_survey, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val rate: TextView = itemView.findViewById(R.id.btnRate)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val number: TextView = itemView.findViewById(R.id.status)
        private val startDate: TextView = itemView.findViewById(R.id.startDate)
        private val endDate: TextView = itemView.findViewById(R.id.endDate)
        private var startTimeZone = ""
        private var endTimeZone = ""

        fun bind(survey: SurveyModel) {

            title.text = survey.title
            number.text = "${survey.questions?.size} Questions"

            startTimeZone = OffsetDateTime.parse(survey.datestart?:"2022-01-01T12:00:00+0100").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            startDate.text = startTimeZone

            endTimeZone = OffsetDateTime.parse(survey.dateend?:"202-01-01T12:00:00+0100").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            endDate.text = endTimeZone

            if (status == SurveyEnum.Evaluate.survey){
                result.visibility = View.VISIBLE
                result.text = itemView.resources.getText(R.string.evaluate)
            }else {
                result.visibility = View.GONE
            }

            when(survey.surveytype){

                SurveyEnum.Rate.survey ->{
                    rate.text = itemView.resources.getText(R.string.rating)
                }

                SurveyEnum.Multi.survey ->{
                    rate.text = itemView.resources.getText(R.string.multiple)

                }

                SurveyEnum.Open.survey ->{
                    rate.text = itemView.resources.getText(R.string.open)
                }
            }

            result.setOnClickListener {
                val bundle = bundleOf(SurveyEnum.Id.survey to survey.id)
                itemView.findNavController().navigate(R.id.action_surveySystemFragment_to_createSurveyFragment,bundle)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<SurveyModel>() {
        override fun areItemsTheSame(
            oldItem: SurveyModel,
            newItem: SurveyModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SurveyModel,
            newItem: SurveyModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}


