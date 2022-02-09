package com.utechia.tdf.survey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.SurveyModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SurveyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var survey: MutableList<SurveyModel> = mutableListOf()
    private var startTimeZone = ""
    private var endTimeZone = ""
    private var evaluated = false




    fun addData(_survey: MutableList<SurveyModel>,_evaluated:Boolean) {
        survey.clear()
        notifyDataSetChanged()
        survey.addAll(_survey)
        notifyItemRangeChanged(0,_survey.size)
        evaluated = _evaluated

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_survey, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = survey.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val rate: TextView = itemView.findViewById(R.id.btnRate)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val number: TextView = itemView.findViewById(R.id.status)
        private val startDate: TextView = itemView.findViewById(R.id.startDate)
        private val endDate: TextView = itemView.findViewById(R.id.endDate)

        fun bind0(position: Int) {

            title.text = survey[position].title
            number.text = "${survey[position].questions?.size} Questions"

            startTimeZone = OffsetDateTime.parse(survey[position].datestart?:"2022-01-01T12:00:00+0100").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            startDate.text = "$startTimeZone"

            endTimeZone = OffsetDateTime.parse(survey[position].dateend?:"202-01-01T12:00:00+0100").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            endDate.text = "$endTimeZone"

            if (survey[position].surveystatus==SurveyEnum.Expired.survey || survey[position].surveystatus==SurveyEnum.Cancelled.survey || evaluated){
                result.visibility = View.GONE
            }else {
                result.visibility = View.VISIBLE
                result.text = itemView.resources.getText(R.string.evaluate)
            }

            when(survey[position].surveytype){

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
                val bundle = bundleOf(SurveyEnum.Id.survey to survey[position].id)
                itemView.findNavController().navigate(R.id.action_surveySystemFragment_to_createSurveyFragment,bundle)
            }
        }
    }
}


