package com.utechia.tdf.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTeaBoyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.food.setOnClickListener {

        }

        binding.drink.setOnClickListener {

        }

        binding.favorite.setOnClickListener {

        }

        binding.order.setOnClickListener {

        }




    }

}