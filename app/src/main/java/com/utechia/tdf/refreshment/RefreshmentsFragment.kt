package com.utechia.tdf.refreshment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.enum.RefreshmentEnum
import com.utechia.tdf.R
import com.utechia.tdf.cart.CartViewModel
import com.utechia.tdf.databinding.FragmentRefreshmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshmentsFragment : Fragment() {

    private lateinit var binding: FragmentRefreshmentBinding
    private val cartViewModel:CartViewModel by viewModels()
        private lateinit var prefs: SharedPreferences
    private var order = 0

    companion object{
        const val Order = "order"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefreshmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, AppCompatActivity.MODE_PRIVATE)

        cartViewModel.getCart()

        order = prefs.getInt(Order,0)

        if (order!=0){
            binding.bubble.visibility = View.VISIBLE
            binding.orderNumber.visibility = View.VISIBLE
            binding.orderNumber.text = order.toString()
            binding.status.text = getString(R.string.preparingStatus)
        }else{
            binding.bubble.visibility = View.GONE
            binding.orderNumber.visibility = View.GONE
            binding.status.text = getString(R.string.daliveryStatus)

        }


        binding.food.setOnClickListener {

            val bundle = bundleOf(RefreshmentEnum.Category.refreshment to RefreshmentEnum.Food.refreshment)
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                bundle
            )
        }

        binding.drink.setOnClickListener {

            val bundle = bundleOf(RefreshmentEnum.Category.refreshment to RefreshmentEnum.Hot.refreshment)
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                bundle

            )

        }

        binding.favorite.setOnClickListener {
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_favoriteFragment,
            )

        }

        binding.order.setOnClickListener {
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_orderFragment,

                )
        }
    }
}