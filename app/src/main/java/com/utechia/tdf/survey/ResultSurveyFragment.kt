package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentResultSurveyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultSurveyFragment : Fragment() {

    private lateinit var binding: FragmentResultSurveyBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appCompatButton.setOnClickListener {
            findNavController().navigate(R.id.resultSurveyFragment_to_surveySystemFragment)
        }

    }
}