package com.utechia.tdf.order.user

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
import com.utechia.tdf.databinding.FragmentPendingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserOrderChildFragment(val order: String) : Fragment() {

    private lateinit var binding: FragmentPendingBinding
    private val userOrderViewModel: UserOrderViewModel by viewModels()
    private val userOrderAdapter: UserOrderAdapter = UserOrderAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userOrderViewModel.getOrder(order)

        binding.refreshLayout.setOnRefreshListener {

            userOrderViewModel.getOrder(order)

        }

        binding.recyclerView.apply {
            adapter = userOrderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(ItemDecorationOrder())
        }

        observer()
    }

    private fun observer() {

        userOrderViewModel.userOrderModel.observe(viewLifecycleOwner) {

            when (it) {

                is Result.Success -> {

                        it.data.observe(viewLifecycleOwner) { it1 ->
                            userOrderAdapter.submitData(lifecycle,it1)
                        }

                    userOrderAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && userOrderAdapter.itemCount < 1) {
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