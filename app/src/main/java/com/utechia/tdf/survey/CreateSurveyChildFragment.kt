package com.utechia.tdf.survey

import android.os.Bundle
import java.util.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.QuestionModel
import com.utechia.tdf.databinding.FragmentCreateSurveyChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSurveyChildFragment(val questionModel: QuestionModel, val type: String) : Fragment() {

    private lateinit var binding: FragmentCreateSurveyChildBinding
    private lateinit var radioButton:RadioButton



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateSurveyChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.questionTitle.text = questionModel.title

        when(type){
            SurveyEnum.Rate.survey -> {
                binding.rating.visibility = View.VISIBLE
                binding.description.visibility = View.GONE
                binding.radioGroup.visibility = View.GONE
            }

            SurveyEnum.Multi.survey -> {
                binding.rating.visibility = View.GONE
                binding.description.visibility = View.GONE
                binding.radioGroup.visibility = View.VISIBLE            }

            SurveyEnum.Open.survey -> {
                binding.rating.visibility = View.GONE
                binding.description.visibility = View.VISIBLE
                binding.radioGroup.visibility = View.GONE               }
        }




      /*  binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                if (answer.size > number) {
                    if (answer[number].isNotEmpty())
                        answer.removeAt(number)
                    answer.add(
                        number,
                        hashMapOf(
                            "rate" to rating.toInt(),
                        )
                    )
                } else
                    answer.add(
                        number,
                        hashMapOf(
                            "rate" to rating.toInt(),
                        )
                    )

            }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<RadioButton>(checkedId)?.let {

            }

        }*/
    }
}