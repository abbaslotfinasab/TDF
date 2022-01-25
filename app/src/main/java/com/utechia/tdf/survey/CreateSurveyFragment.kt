package com.utechia.tdf.survey

import android.os.Bundle
import java.util.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
    private val answer:ArrayList<HashMap<String,Any>> = ArrayList()
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
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                if (answer.size>number) {
                    if (answer[number].isNotEmpty())
                        answer.removeAt(number)
                    answer.add(number, hashMapOf("rate" to rating.toInt(),"question" to survey[0].questions!![number].id!!))
                }
                else
                    answer.add(number, hashMapOf("rate" to rating.toInt(),"question" to survey[0].questions!![number].id!!))

            }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<RadioButton>(checkedId)?.let {

                if (answer.size>number) {
                    if (answer[number].isNotEmpty())
                        answer.removeAt(number)
                    answer.add(number, hashMapOf("option" to it.text.toString(),"question" to survey[0].questions!![number].id!!))
                }
                else
                    answer.add(number, hashMapOf("option" to it.text.toString(),"question" to survey[0].questions!![number].id!!))
            }
        }


        binding.btnNext.setOnClickListener {

            if (binding.description.text.toString() != "")
            answer.add(number, hashMapOf("text" to binding.description.text.toString(), "question" to survey[0].questions!![number].id!!))

            when {
                number<survey[0].questions!!.size-1 -> {
                    number += 1
                    askQuestion()
                }
                answer.size!=0 -> {
                    val bundle = Bundle()
                    bundle.putSerializable("answer", answer)
                    findNavController().navigate(
                        R.id.createSurveyFragment_to_confirmSurveyFragment,
                        bundle
                    )
                }
                else -> Toast.makeText(context,"No value for evaluate",Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnPrevious.setOnClickListener {

            if (binding.description.text.toString() != "")
            answer.add(number, hashMapOf("text" to binding.description.text.toString()))

            if (number>0) {
                number -= 1
                askQuestion()
            }
            else
                findNavController().navigateUp()

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

    private fun askQuestion(){

        if(number==survey[0].questions?.size!!-1)
            binding.btnNext.text = "Evaluate"
        else
            binding.btnNext.text = "Next"

        binding.textView22.text = "${number+1}/${survey[0].questions!!.size}"
        binding.questionTitle.text = survey[0].questions!![number].title

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


                binding.radioGroup.clearCheck()
                binding.radioGroup.removeAllViews()

                for (i in 0 until survey[0].questions!![number].options!!.size){
                    radioButton = RadioButton(context,null,0, R.style.CheckBox)
                    radioButton.text = survey[0].questions!![number].options!![i]
                    radioButton.id = i
                    binding.radioGroup.addView(radioButton)

                }
            }

            "open_end" ->{

                binding.description.setText("")
                binding.description.visibility = View.VISIBLE
                binding.radioGroup.visibility = View.GONE
                binding.rating.visibility = View.GONE

            }
        }
    }
}