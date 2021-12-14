package com.utechia.tdf.survey

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentConfirmSurveyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmSurveyFragment : DialogFragment() {

    private lateinit var binding: FragmentConfirmSurveyBinding

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


        binding.btnKeep.setOnClickListener {
            findNavController().navigate(R.id.confirmSurveyFragment_to_resultSurveyFragment)
            dialog?.dismiss()

        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.confirmSurveyFragment_to_surveySystemFragment)
            dialog?.dismiss()


        }


    }

}