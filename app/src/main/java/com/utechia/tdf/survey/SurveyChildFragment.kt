package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentSurveyChildBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyChildFragment(val survey: String) : Fragment() {

    private lateinit var binding: FragmentSurveyChildBinding
    private val surveyViewModel:SurveyViewModel by viewModels()
    private val surveyAdapter: SurveyAdapter = SurveyAdapter(survey)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSurveyChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        surveyViewModel.getSurveyList(survey)

        binding.refreshLayout.setOnRefreshListener {
            surveyViewModel.getSurveyList(survey)
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
                    it.data.observe(viewLifecycleOwner) { it1 ->
                        surveyAdapter.submitData(lifecycle,it1)
                    }

                    surveyAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && surveyAdapter.itemCount < 1) {
                            binding.recyclerView.visibility = View.GONE
                            binding.prg.visibility = View.GONE
                            binding.emptyLayout.visibility = View.VISIBLE
                            binding.refreshLayout.isRefreshing = false

                        }else if (loadState.source.refresh is LoadState.Loading ){
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = true
                            binding.recyclerView.visibility = View.GONE
                            binding.emptyLayout.visibility = View.GONE
                        }else{
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = false
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }
                }

                is Result.Loading -> {}

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