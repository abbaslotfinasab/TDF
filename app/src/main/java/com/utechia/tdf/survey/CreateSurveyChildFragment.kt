package com.utechia.tdf.survey

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.QuestionModel
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateSurveyChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSurveyChildFragment(
    private val questionModel: QuestionModel,
    val type: String,
    private val position: Int
) : Fragment() {

    private lateinit var binding: FragmentCreateSurveyChildBinding
    private lateinit var navHostFragment : NavHostFragment
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

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment
        val parent = navHostFragment.childFragmentManager.primaryNavigationFragment as CreateSurveyFragment


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
                binding.radioGroup.visibility = View.VISIBLE

                binding.radioGroup.clearCheck()
                binding.radioGroup.removeAllViews()

                if (questionModel.options?.isNotEmpty() == true) {
                    for (i in 0 until questionModel.options?.size!!) {
                        radioButton = RadioButton(context, null, 0, R.style.CheckBox)
                        radioButton.text = questionModel.options!![i]
                        radioButton.id = i
                        binding.radioGroup.addView(radioButton)
                    }
                }
            }

            SurveyEnum.Open.survey -> {
                binding.rating.visibility = View.GONE
                binding.description.visibility = View.VISIBLE
                binding.radioGroup.visibility = View.GONE
            }
        }

        binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                if ( parent.surveyViewModel.answer.size-1>=position) {
                    for (i in 0 until parent.surveyViewModel.answer.size-1) {
                        if (parent.surveyViewModel.answer[i][SurveyEnum.Question.survey] == questionModel.id!!) {
                            parent.surveyViewModel.answer.removeAt(i)
                        }
                    }

                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Rate.survey to rating.toInt()
                        )
                    )

                }else{
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Rate.survey to rating.toInt()
                        )
                    )
                }
            }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<RadioButton>(checkedId)?.let {
                if ( parent.surveyViewModel.answer.size-1>=position) {
                    for (i in 0 until parent.surveyViewModel.answer.size-1) {
                        if (parent.surveyViewModel.answer[i][SurveyEnum.Question.survey] == questionModel.id!!) {
                            parent.surveyViewModel.answer.removeAt(i)
                        }
                    }

                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Option.survey to it.text.toString()
                        )
                    )
                }else{
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Option.survey to it.text.toString()
                        )
                    )
                }
            }
        }

        binding.description.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              /*   if ( parent.surveyViewModel.answer.size-1>=position) {
                    parent.surveyViewModel.answer.removeAt(position)
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Text.survey to s.toString()
                        )
                    )
                }else{
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Text.survey to s.toString()
                        )
                    )
                }*/
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             /*    if ( parent.surveyViewModel.answer.size-1>=position) {
                    parent.surveyViewModel.answer.removeAt(position)
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Text.survey to s.toString()
                        )
                    )
                }else{
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Text.survey to s.toString()
                        )
                    )
                }*/
            }

            override fun afterTextChanged(s: Editable?) {

                if ( parent.surveyViewModel.answer.size-1>=position) {
                    parent.surveyViewModel.answer.removeAt(position)
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Text.survey to s.toString()
                        )
                    )
                }else{
                    parent.surveyViewModel.answer.add(
                        hashMapOf(
                            SurveyEnum.Question.survey to questionModel.id!!,
                            SurveyEnum.Text.survey to s.toString()
                        )
                    )
                }
            }
        })
    }
}