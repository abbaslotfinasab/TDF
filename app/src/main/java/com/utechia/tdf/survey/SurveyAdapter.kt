package com.utechia.tdf.survey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.SurveyModel
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class SurveyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var survey: MutableList<SurveyModel> = mutableListOf()

    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var dateFormat: Date
    private var simple = ""

    fun addData(_survey: MutableList<SurveyModel>) {
        survey.clear()
        survey.addAll(_survey)
        notifyDataSetChanged()
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
        private val rate: AppCompatButton = itemView.findViewById(R.id.btnRate)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val number: TextView = itemView.findViewById(R.id.status)
        private val startDate: TextView = itemView.findViewById(R.id.startDate)
        private val endDate: TextView = itemView.findViewById(R.id.endDate)

        fun bind0(position: Int) {

            title.text = survey[position].title
            number.text = "${survey[position].questions?.size}Questions"

            dateFormat = sdf.parse(survey[position].datestart)
            simple = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.getDefault()).format(dateFormat)
            startDate.text = "$simple"

            dateFormat = sdf.parse(survey[position].dateend)
            simple = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.getDefault()).format(dateFormat)
            endDate.text = "$simple"

            result.text = "Evaluate"

            result.setOnClickListener {
                val bundle = bundleOf("surveyId" to survey[position].id)
                itemView.findNavController().navigate(R.id.action_surveySystemFragment_to_createSurveyFragment,bundle)
            }

            rate.text = survey[position].surveytype
        }
    }
}


