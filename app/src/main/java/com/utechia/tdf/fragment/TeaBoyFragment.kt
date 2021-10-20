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
            unselect()
            it.setBackgroundResource(R.mipmap.foodselected)
        }

        binding.drink.setOnClickListener {
            unselect()
            it.setBackgroundResource(R.mipmap.drinkselected)
        }

        binding.favorite.setOnClickListener {
            unselect()
            it.setBackgroundResource(R.mipmap.favoriteselected)
        }

        binding.order.setOnClickListener {
            unselect()
            it.setBackgroundResource(R.mipmap.orderselected)
        }




    }

    private fun unselect() {

        binding.food.setBackgroundResource(R.mipmap.food)
        binding.drink.setBackgroundResource(R.mipmap.drink)
        binding.favorite.setBackgroundResource(R.mipmap.favorit)
        binding.order.setBackgroundResource(R.mipmap.orders)

    }


}