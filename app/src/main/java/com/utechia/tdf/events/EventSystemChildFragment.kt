package com.utechia.tdf.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.utechia.domain.utile.Result
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.tdf.databinding.FragmentEventSystemChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventSystemChildFragment(val event:String) : Fragment() {

    private lateinit var binding: FragmentEventSystemChildBinding

    private val eventViewModel:EventViewModel by viewModels()

    private val eventAdapter:EventAdapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventSystemChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        eventViewModel.getAllEvent(event)

        binding.refreshLayout.setOnRefreshListener {

            eventViewModel.getAllEvent(event)

        }

        binding.recyclerView.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(EventItemDecoration())
        }

        observer()

    }

    private fun observer() {

        eventViewModel.event.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = false

                    it.data.observe(viewLifecycleOwner) { it1 ->
                        eventAdapter.submitData(lifecycle,it1)
                    }

                    eventAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && eventAdapter.itemCount < 1) {
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
                    binding.refreshLayout.isRefreshing = false
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}