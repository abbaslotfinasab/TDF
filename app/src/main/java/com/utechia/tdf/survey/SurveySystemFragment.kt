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
import com.google.android.material.tabs.TabLayout
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentSurveySystemBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveySystemFragment : Fragment() {

    private lateinit var binding: FragmentSurveySystemBinding
    private val surveyViewModel:SurveyViewModel by viewModels()
    private val surveyAdapter: SurveyAdapter = SurveyAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSurveySystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surveyViewModel.getSurveyList()
        observer()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        surveyViewModel.getSurveyList()
                        observer()

                    }
                    else -> {
                        surveyViewModel.getEvaluate()
                        observer()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                /*when(tab?.position){

                    0 -> {
                        surveyViewModel.getSurveyList()
                        observer()

                    }
                    1 -> {
                        surveyViewModel.getEvaluate()
                        observer()
                    }
                }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

              /*  when(tab?.position){

                    0 -> {
                        surveyViewModel.getSurveyList()
                        observer()

                    }
                    1 -> {
                        surveyViewModel.getEvaluate()
                        observer()
                    }
                }*/
            }

        })

        binding.appBackButton.setOnClickListener {

            findNavController().navigateUp()

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
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        surveyAdapter.addData(it.data)


                    }
                    else{
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                    }

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}