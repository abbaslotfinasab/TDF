package com.utechia.tdf.order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentTeaBoyOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyOrdersFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyOrdersBinding
    val orderViewModel: OrderViewModel by viewModels()
    private val orderAdapter: OrderTeaBoyAdapter = OrderTeaBoyAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel.getOrderTeaBoy("pending")

        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        orderViewModel.getOrderTeaBoy("pending")
                        observer()
                    }
                    1 -> {
                        orderViewModel.getOrderTeaBoy("delivered")
                        observer()
                    }
                    2 -> {
                        orderViewModel.getOrderTeaBoy("cancelled")
                        observer()
                    }
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

               /* when(tab?.position){

                        orderViewModel.getOrderTeaBoy("pending")
                        observer()
                    }
                    1 -> {
                        orderViewModel.getOrderTeaBoy("delivered")
                        observer()
                    }
                    2 -> {
                        orderViewModel.getOrderTeaBoy("cancelled")
                        observer()
                    }
                }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

              /*  when(tab?.position){

                    0 -> {
                        orderViewModel.getOrderTeaBoy("pending")
                        observer()
                    }
                    1 -> {
                        orderViewModel.getOrderTeaBoy("delivered")
                        observer()
                    }
                    2 -> {
                        orderViewModel.getOrderTeaBoy("cancelled")
                        observer()
                    }
                }*/
            }

        })

        binding.recyclerView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationOrder())
        }


        observer()

    }

    private fun observer() {
        orderViewModel.orderModel.observe(viewLifecycleOwner){


            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        orderAdapter.addData(it.data)

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
                    /*Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()*/
                }
            }
        }

    }
}