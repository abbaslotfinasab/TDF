package com.utechia.tdf.ticket

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
import com.utechia.tdf.databinding.FragmentTicketChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketChildFragment(val ticket: String) : Fragment() {

    private lateinit var binding: FragmentTicketChildBinding
    private val ticketViewModel:TicketViewModel by viewModels()
    private val ticketAdapter:TicketAdapter = TicketAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketViewModel.getAllTicket(ticket)

        binding.refreshLayout.setOnRefreshListener {
            ticketViewModel.getAllTicket(ticket)
        }



        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_ticketSystemFragment_to_createTicketFragment)

        }

        binding.more.setOnClickListener {
            findNavController().navigate(R.id.action_ticketSystemFragment_to_createTicketFragment)
        }

        binding.recyclerView.apply {
            adapter = ticketAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(TicketItemDecortation())
        }

        observer()

    }

    private fun observer() {
        ticketViewModel.ticketModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {

                    it.data.observe(viewLifecycleOwner) { it1 ->
                        ticketAdapter.submitData(lifecycle,it1)
                    }

                    ticketAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && ticketAdapter.itemCount < 1) {
                            binding.recyclerView.visibility = View.GONE
                            binding.prg.visibility = View.GONE
                            binding.title.visibility = View.GONE
                            binding.plus.visibility = View.GONE
                            binding.more.visibility = View.VISIBLE
                            binding.imageView4.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = false

                        }else if (loadState.source.refresh is LoadState.Loading ){
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = true
                            binding.recyclerView.visibility = View.GONE
                            binding.title.visibility = View.GONE
                            binding.plus.visibility = View.GONE
                            binding.more.visibility = View.VISIBLE
                            binding.imageView4.visibility = View.GONE                        }else{
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = false
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }
                }

                is Result.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.title.visibility = View.GONE
                    binding.plus.visibility = View.GONE
                    binding.more.visibility = View.GONE
                    binding.imageView4.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.title.visibility = View.GONE
                    binding.plus.visibility = View.GONE
                    binding.more.visibility = View.GONE
                    binding.imageView4.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}