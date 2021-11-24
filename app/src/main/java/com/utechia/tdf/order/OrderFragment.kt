package com.utechia.tdf.order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        orderViewModel.getOrder("waiting")

        binding.recyclerView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationOrder())
        }

        binding.appBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.title.setOnClickListener {
            binding.title.setTextColor(Color.BLACK)
            binding.subtitle.setTextColor(Color.parseColor("#9D9D9D"))
            binding.sub.setTextColor(Color.parseColor("#9D9D9D"))
            binding.line.visibility = View.GONE
            binding.subLine.visibility = View.GONE
            binding.titleLine.visibility = View.VISIBLE
            orderViewModel.getOrder("delivered")
            observer()
        }

        binding.subtitle.setOnClickListener {
            binding.subtitle.setTextColor(Color.BLACK)
            binding.title.setTextColor(Color.parseColor("#9D9D9D"))
            binding.sub.setTextColor(Color.parseColor("#9D9D9D"))
            binding.line.visibility = View.GONE
            binding.subLine.visibility = View.VISIBLE
            binding.titleLine.visibility = View.GONE
            orderViewModel.getOrder("waiting")
            observer()


        }

        binding.sub.setOnClickListener {
            binding.sub.setTextColor(Color.BLACK)
            binding.title.setTextColor(Color.parseColor("#9D9D9D"))
            binding.subtitle.setTextColor(Color.parseColor("#9D9D9D"))
            binding.line.visibility = View.VISIBLE
            binding.subLine.visibility = View.GONE
            binding.titleLine.visibility = View.GONE
            orderViewModel.getOrder("cancelled")
            observer()


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