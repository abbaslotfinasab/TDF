package com.utechia.tdf.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReservationChildBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import com.utechia.tdf.survey.SurveyAdapter
import com.utechia.tdf.survey.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationChildFragment(val reservation: String?= SurveyEnum.Evaluate.survey) : Fragment() {

    private lateinit var binding: FragmentReservationChildBinding
    private val surveyViewModel: SurveyViewModel by viewModels()
    private val surveyAdapter: SurveyAdapter = SurveyAdapter(reservation.toString())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        surveyViewModel.getSurveyList(reservation.toString())

        binding.more.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_createReservationFragment)
        }

        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_createReservationFragment)
        }

        binding.refreshLayout.setOnRefreshListener {
            surveyViewModel.getSurveyList(reservation.toString())
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
                            binding.createLayout.visibility = View.VISIBLE
                            binding.refreshLayout.isRefreshing = false

                        }else if (loadState.source.refresh is LoadState.Loading ){
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = true
                            binding.recyclerView.visibility = View.GONE
                            binding.createLayout.visibility = View.GONE
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
                    binding.createLayout.visibility = View.VISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}