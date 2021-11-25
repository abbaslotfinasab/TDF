package com.utechia.tdf.order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.databinding.FragmentTeaBoyOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyOrdersFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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





    }

}