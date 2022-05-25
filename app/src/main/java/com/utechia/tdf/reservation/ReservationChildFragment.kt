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
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReservationChildBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationChildFragment(val reservation: String?= "false") : Fragment() {

    private lateinit var binding: FragmentReservationChildBinding
    private val meetingViewModel:MeetingViewModel by viewModels()
    private val reservationAdapter:ReservationAdapter = ReservationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationChildBinding.inflate(inflater, container, false)
        binding.plus.bringToFront()
        binding.more.bringToFront()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        meetingViewModel.getAllMeeting(reservation.toString())

        binding.more.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_createReservationFragment)
        }

        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_createReservationFragment)
        }

        binding.refreshLayout.setOnRefreshListener {
            meetingViewModel.getAllMeeting(reservation.toString())
        }

        binding.recyclerView.apply {
            adapter = reservationAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationOrder())
        }

        observer()
    }

    private fun observer() {

        meetingViewModel.meetingModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    it.data.observe(viewLifecycleOwner) { it1 ->
                        reservationAdapter.submitData(lifecycle,it1)
                    }

                    reservationAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && reservationAdapter.itemCount < 1) {
                            binding.recyclerView.visibility = View.GONE
                            binding.prg.visibility = View.GONE
                            binding.createLayout.visibility = View.VISIBLE
                            binding.more.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = false

                        }else if (loadState.source.refresh is LoadState.Loading ){
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = true
                            binding.recyclerView.visibility = View.GONE
                            binding.more.visibility = View.GONE
                            binding.createLayout.visibility = View.GONE
                        }else{
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = false
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.createLayout.visibility = View.GONE
                            binding.more.visibility = View.VISIBLE
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