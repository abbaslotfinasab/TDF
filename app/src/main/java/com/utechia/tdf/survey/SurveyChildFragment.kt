package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentSurveyChildBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyChildFragment(val survey: String) : Fragment() {

    private lateinit var binding: FragmentSurveyChildBinding
    private val surveyViewModel:SurveyViewModel by viewModels()
    private val surveyAdapter: SurveyAdapter = SurveyAdapter()
    private var evaluated = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSurveyChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        evaluated = if (survey == SurveyEnum.Evaluate.survey) {
            surveyViewModel.getSurveyList()
            false
        }else{
            surveyViewModel.getEvaluate()
            true

        }

        binding.refreshLayout.setOnRefreshListener {

            evaluated = if (survey == SurveyEnum.Evaluate.survey) {
                surveyViewModel.getSurveyList()
                false
            }else{
                surveyViewModel.getEvaluate()
                true

            }

        }

        binding.recyclerView.apply {
            adapter = surveyAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationOrder())
        }

        observer()

    }

    private fun observer() {

        surveyViewModel.survey.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        surveyAdapter.addData(it.data,evaluated)


                    }
                    else{
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                    }

                }

                is Result.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = false
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}