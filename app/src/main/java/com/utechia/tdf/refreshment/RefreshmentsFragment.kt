package com.utechia.tdf.refreshment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentRefreshmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshmentsFragment : Fragment() {

    private lateinit var binding: FragmentRefreshmentBinding
    private val refreshmentViewModel: RefreshmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefreshmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.food.setOnClickListener {

            val bundle = bundleOf("category" to "food")
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                bundle
            )
        }

        binding.drink.setOnClickListener {

            val bundle = bundleOf("category" to "hot_drink")
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

    fun observer(){
        refreshmentViewModel.refreshmentModel.observe(viewLifecycleOwner){


        }

    }
}