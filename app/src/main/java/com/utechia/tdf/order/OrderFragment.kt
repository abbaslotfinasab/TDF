package com.utechia.tdf.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val orderViewModel: OrderViewModel by viewModels()
    private val orderAdapter: OrderAdapter = OrderAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel.getOrder("pending")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        orderViewModel.getOrder("pending")
                        observer()

                    }
                    1 -> {
                        orderViewModel.getOrder("delivered")
                        observer()
                    }
                    2 -> {
                        orderViewModel.getOrder("cancelled")
                        observer()
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                /* when(tab?.position){

                    0 -> {
                        orderViewModel.getOrder("pending")
                        observer()

                    }
                    1 -> {
                        orderViewModel.getOrder("delivered")
                        observer()
                    }
                    2 -> {
                        orderViewModel.getOrder("cancelled")
                        observer()
                    }
                 }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                /*  when(tab?.position){

                     0 -> {
                        orderViewModel.getOrder("pending")
                        observer()

                    }
                    1 -> {
                        orderViewModel.getOrder("delivered")
                        observer()
                    }
                    2 -> {
                        orderViewModel.getOrder("cancelled")
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

        binding.appBackButton.setOnClickListener {
            findNavController().popBackStack()
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