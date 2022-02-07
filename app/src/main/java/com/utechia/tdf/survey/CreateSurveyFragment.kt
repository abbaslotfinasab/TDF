package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateSurveyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSurveyFragment : Fragment() {

    private lateinit var binding: FragmentCreateSurveyBinding
    private val surveyViewModel:SurveyViewModel by viewModels()
    private lateinit var createSurveyViewPagerAdapter: CreateSurveyViewPagerAdapter
    private var surveyId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            surveyId = requireArguments().getInt(SurveyEnum.Id.survey, 0)
        }

        surveyViewModel.getSurvey(surveyId)


        observer()
    }

    private fun observer() {

        surveyViewModel.survey.observe(viewLifecycleOwner){it1 ->

            when (it1) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    if (it1.data[0].questions.isNullOrEmpty()){
                        binding.numberText.text = "0/0"
                        binding.pager.visibility = View.GONE
                        binding.btnNext.visibility = View.GONE
                        binding.btnPrevious.visibility = View.GONE
                        Toast.makeText(context, "No question Found", Toast.LENGTH_SHORT).show()

                    }else {
                        binding.numberText.text = "${binding.pager.currentItem+1}/${it1.data[0].questions?.size}"
                        binding.pager.visibility = View.VISIBLE
                        createSurveyViewPagerAdapter = CreateSurveyViewPagerAdapter(
                            childFragmentManager,
                            lifecycle,
                            it1.data[0].surveytype ?: ""
                        )
                        it1.data[0].questions?.let { createSurveyViewPagerAdapter.addData(it) }

                        binding.btnNext.apply {
                            visibility = View.VISIBLE
                            binding.btnNext.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.confirm))
                            if (binding.pager.currentItem<it1.data[0].questions?.size?:0) {
                                binding.btnNext.text = getText(R.string.next)
                                setOnClickListener {
                                    binding.pager.setCurrentItem(binding.pager.currentItem + 1, true)
                                    binding.numberText.text = "${binding.pager.currentItem+1}/${it1.data[0].questions?.size}"
                                }
                            }else{
                                binding.btnNext.text = getText(R.string.evaluate)
                                setOnClickListener {
                                    //set survey
                                }
                            }
                        }

                        binding.btnPrevious.apply {
                            visibility = View.VISIBLE
                            if (binding.pager.currentItem>0) {
                                binding.btnPrevious.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.accepted))
                                setOnClickListener {
                                    binding.pager.setCurrentItem(binding.pager.currentItem - 1, true)
                                }
                            }
                            else {
                                binding.btnPrevious.setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireActivity(),
                                        R.color.disActive
                                    )
                                )
                                isEnabled = false
                            }
                        }
                    }
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it1.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}