package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateSurveyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSurveyFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentCreateSurveyBinding
    val surveyViewModel: SurveyViewModel by viewModels()
    private lateinit var createSurveyViewPagerAdapter: CreateSurveyViewPagerAdapter
    private var questionSize: Int = 0
    private var surveyId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateSurveyBinding.inflate(inflater, container, false)
        binding.btnNext.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            surveyId = requireArguments().getInt(SurveyEnum.Id.survey, 0)
        }
        surveyViewModel.getSurvey(surveyId)

        binding.pager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.numberText.text =
                    "${binding.pager.currentItem + 1}/$questionSize"
                when{
                    position==0 -> {
                        firstPage()
                    }

                    position==questionSize-1 ->{
                        lastPage()
                    }

                    position<questionSize ->{
                        nextPage()
                    }
                }
            }
        })

        observer()
    }

    private fun observer() {

        surveyViewModel.survey.observe(viewLifecycleOwner) { it1 ->

            when (it1) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    questionSize = it1.data[0].questions?.size ?: 0

                    if (it1.data[0].questions.isNullOrEmpty()) {
                        binding.numberText.text = "0/0"
                        binding.pager.visibility = View.GONE
                        binding.btnNext.visibility = View.GONE
                        binding.btnPrevious.visibility = View.GONE
                        Toast.makeText(context, "No question Found", Toast.LENGTH_SHORT).show()

                    } else {
                        firstPage()
                        binding.numberText.text =
                            "${binding.pager.currentItem + 1}/${it1.data[0].questions?.size}"
                        binding.pager.visibility = View.VISIBLE
                        createSurveyViewPagerAdapter = CreateSurveyViewPagerAdapter(
                            childFragmentManager,
                            lifecycle,
                            it1.data[0].surveytype ?: "",
                            it1.data[0].questions ?: emptyList()
                        )
                        binding.pager.adapter = createSurveyViewPagerAdapter
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

    private fun firstPage() {

        binding.btnPrevious.apply {
            visibility = View.VISIBLE
            isEnabled = false
            binding.btnPrevious.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.disActive
                )
            )

        }

        if (questionSize > 1) {
            binding.btnNext.apply {
                visibility = View.VISIBLE
                text = getText(R.string.next)
                binding.btnNext.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.confirm
                    )
                )
            }
        } else {
            binding.btnNext.apply {
                visibility = View.VISIBLE
                text = getText(R.string.evaluate)
                binding.btnNext.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.confirm
                    )
                )
            }
        }
    }

    private fun nextPage() {

        binding.btnPrevious.apply {
            visibility = View.VISIBLE
            isEnabled = true
            binding.btnPrevious.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.accepted
                )
            )

        }

        binding.btnNext.apply {
            visibility = View.VISIBLE
            text = getText(R.string.next)
            binding.btnNext.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.confirm
                )
            )
        }
    }

    private fun lastPage() {

        binding.btnPrevious.apply {
            visibility = View.VISIBLE
            isEnabled = true
            binding.btnPrevious.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.accepted
                )
            )

        }

        binding.btnNext.apply {
            visibility = View.VISIBLE
            text = getText(R.string.evaluate)
            binding.btnNext.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.confirm
                )
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnNext -> {
                if (binding.pager.currentItem < questionSize-1) {
                    binding.pager.setCurrentItem(binding.pager.currentItem + 1, true)
                } else {
                    val bundle = bundleOf("answer" to surveyViewModel.answer)
                    findNavController().navigate(R.id.createSurveyFragment_to_confirmSurveyFragment,bundle)
                }
            }

            R.id.btnPrevious -> {
                if (binding.pager.currentItem > 0) {
                    binding.pager.setCurrentItem(binding.pager.currentItem - 1, true)
                }
            }
        }
    }
}