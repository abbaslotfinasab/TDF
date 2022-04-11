package com.utechia.tdf.order.teaboy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentTeaBoyOrderChildBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyOrderChildFragment (var order: String?= OrderEnum.Pending.order) : Fragment() {

    private lateinit var binding: FragmentTeaBoyOrderChildBinding
    val teaBoyOrderViewModel:TeaBoyOrderViewModel by viewModels()
    private val orderTeaBoyAdapter:OrderTeaBoyAdapter = OrderTeaBoyAdapter(this)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyOrderChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order?.let { teaBoyOrderViewModel.getOrderTeaBoy(it) }

        binding.refreshLayout.setOnRefreshListener {

            order?.let { teaBoyOrderViewModel.getOrderTeaBoy(it) }

        }


        binding.recyclerView.apply {
            adapter = orderTeaBoyAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationOrder())
        }

        observer()
    }

    private fun observer() {
        teaBoyOrderViewModel.orderModel.observe(viewLifecycleOwner){


            when (it) {
                is Result.Success -> {
                    it.data.observe(viewLifecycleOwner) { it1 ->
                        orderTeaBoyAdapter.submitData(lifecycle,it1)
                    }

                    orderTeaBoyAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && orderTeaBoyAdapter.itemCount < 1) {
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