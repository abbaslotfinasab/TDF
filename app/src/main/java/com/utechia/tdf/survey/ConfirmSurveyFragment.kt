package com.utechia.tdf.survey

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.AnswerModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentConfirmSurveyBinding
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray


@AndroidEntryPoint
class ConfirmSurveyFragment : DialogFragment() {

    private lateinit var binding: FragmentConfirmSurveyBinding
    private val surveyViewModel: SurveyViewModel by viewModels()
    private lateinit var answer:ArrayList<*>
    private lateinit var jsonArray: JSONArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmSurveyBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null) {
            answer =
                (requireArguments().getSerializable("answer") as ArrayList<*>)
        }
        jsonArray = JSONArray(answer)

        binding.btnKeep.setOnClickListener {

            surveyViewModel.postAnswer(jsonArray)
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()

        }

        observer()

    }

    private fun observer() {
        surveyViewModel.survey.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.confirmSurveyFragment_to_resultSurveyFragment)


                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnKeep.isEnabled = false
                    binding.btnCancel.isEnabled = false
                    binding.exit.isEnabled = false

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.confirmSurveyFragment_to_surveySystemFragment)

                }
            }
        }
    }
}