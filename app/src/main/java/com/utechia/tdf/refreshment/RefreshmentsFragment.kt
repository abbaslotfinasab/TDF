package com.utechia.tdf.refreshment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.enum.RefreshmentEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.cart.CartViewModel
import com.utechia.tdf.databinding.FragmentRefreshmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshmentsFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentRefreshmentBinding
    private val refreshmentViewModel:RefreshmentViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
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
        binding.food.setOnClickListener(this)
        binding.drink.setOnClickListener(this)
        binding.favorite.setOnClickListener(this)
        binding.order.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, AppCompatActivity.MODE_PRIVATE)
        order = prefs.getInt(Order,0)

        refreshmentViewModel.getRefreshment("")

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
        refreshmentObserver()

        cartObserver()
    }

    private fun refreshmentObserver() {

        refreshmentViewModel.refreshmentModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    cartViewModel.getCart()
                    binding.food.isEnabled = false
                    binding.drink.isEnabled = false


                }
                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.food.isEnabled = false
                    binding.drink.isEnabled = false

                }

                is Result.Error -> {
                    binding.food.isEnabled = true
                    binding.drink.isEnabled = true
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){

            R.id.food ->{
                val bundle = bundleOf(RefreshmentEnum.Category.refreshment to RefreshmentEnum.Food.refreshment)
                findNavController().navigate(
                    R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                    bundle
                )
            }

            R.id.drink ->{
                val bundle = bundleOf(RefreshmentEnum.Category.refreshment to RefreshmentEnum.Hot.refreshment)
                findNavController().navigate(
                    R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                    bundle

                )
            }

            R.id.favorite ->{
                findNavController().navigate(
                    R.id.action_refreshmentFragment_to_favoriteFragment,
                )

            }

            R.id.order ->{
                findNavController().navigate(
                    R.id.action_refreshmentFragment_to_orderFragment,

                    )
            }
        }
    }

    private fun cartObserver() {

        cartViewModel.cartModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.drink.isEnabled = true
                    binding.food.isEnabled = true
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}