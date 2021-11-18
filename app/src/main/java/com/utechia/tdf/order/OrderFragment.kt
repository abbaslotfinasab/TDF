package com.utechia.tdf.order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentOrderBinding
import com.utechia.tdf.refreshment.RefreshmentViewModel
import com.utechia.tdf.reservation.ItemDecorationBooked
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

   private lateinit var binding: FragmentOrderBinding
    private val refreshmentViewModel: RefreshmentViewModel by viewModels()
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

        binding.recyclerView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationBooked())
        }

        binding.appCompatButton.setOnClickListener {
/*
            findNavController().navigate(R.id.action_orderFragment_to_orderResultFragment)
*/
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
        }

        binding.subtitle.setOnClickListener {
            binding.subtitle.setTextColor(Color.BLACK)
            binding.title.setTextColor(Color.parseColor("#9D9D9D"))
            binding.sub.setTextColor(Color.parseColor("#9D9D9D"))
            binding.line.visibility = View.GONE
            binding.subLine.visibility = View.VISIBLE
            binding.titleLine.visibility = View.GONE


        }

        binding.sub.setOnClickListener {
            binding.sub.setTextColor(Color.BLACK)
            binding.title.setTextColor(Color.parseColor("#9D9D9D"))
            binding.subtitle.setTextColor(Color.parseColor("#9D9D9D"))
            binding.line.visibility = View.VISIBLE
            binding.subLine.visibility = View.GONE
            binding.titleLine.visibility = View.GONE


        }

        observer()

    }

    private fun observer() {

    }
}