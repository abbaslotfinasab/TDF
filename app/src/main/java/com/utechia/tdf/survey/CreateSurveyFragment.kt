package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.utechia.domain.model.SurveyModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateSurveyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSurveyFragment : Fragment() {

    private lateinit var binding: FragmentCreateSurveyBinding
    private val surveyViewModel:SurveyViewModel by viewModels()
    private val survey:MutableList<SurveyModel> = mutableListOf()
    private lateinit var radioButton:RadioButton
    private var surveyId = 0
    private var number = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            surveyId = requireArguments().getInt("surveyId", 0)

        surveyViewModel.getSurvey(surveyId)

        binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            }

        binding.btnNext.setOnClickListener {

            if (number<survey[0].questions!!.size-1)
                number += 1
            askQuestion()

        }

        binding.btnPrevious.setOnClickListener {
            if (number>0)
                number -= 1
            askQuestion()
        }

        observer()

    }

    private fun observer() {

        surveyViewModel.survey.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    survey.addAll(it.data)
                    askQuestion()
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun askQuestion(){

        binding.textView22.text = "${number+1}/${survey[0].questions!!.size}"
        binding.questionTitle.text = survey[0].questions!![number].title

        if (number == survey[0].questions!!.size-1)
            binding.btnNext.text = "Evaluate"
        else
            binding.btnNext.text = "Next"


        when(survey[0].surveytype){

            "rate" ->{
                binding.description.visibility = View.GONE
                binding.radioGroup.visibility = View.GONE
                binding.rating.visibility = View.VISIBLE

            }

            "multi" ->{
                binding.description.visibility = View.GONE
                binding.radioGroup.visibility = View.VISIBLE
                binding.rating.visibility = View.GONE

                binding.radioGroup.removeAllViews()

                for (i in 0 until survey[0].questions!![number].options!!.size){
                    radioButton = RadioButton(context,null,0, R.style.Widget_AppCompat_CompoundButton_CheckBox)
                    radioButton.text = survey[0].questions!![number].options!![i]
                    radioButton.id = i
                    binding.radioGroup.addView(radioButton)

                }

            }

            "open_end" ->{
                binding.description.visibility = View.VISIBLE
                binding.radioGroup.visibility = View.GONE
                binding.rating.visibility = View.GONE

            }
        }

    }
}