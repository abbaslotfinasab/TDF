package com.utechia.tdf.refreshment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentDeleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteFragment : DialogFragment() {

    private lateinit var binding: FragmentDeleteBinding
    private val cartViewModel:CartViewModel by viewModels()
    private var foodId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            foodId = requireArguments().getInt("foodId", 0)

        binding.btnDelete.setOnClickListener {

            cartViewModel.deleteCart(foodId)
            observer()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().clearBackStack(R.id.deleteFragment)
            findNavController().navigate(R.id.action_deleteFragment_to_cartFragment)
            dialog?.dismiss()

        }

        binding.exit.setOnClickListener {
            findNavController().clearBackStack(R.id.deleteFragment)
            findNavController().navigate(R.id.action_deleteFragment_to_cartFragment)
            dialog?.dismiss()

        }

    }

    private fun observer() {

        cartViewModel.cartModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().clearBackStack(R.id.deleteFragment)
                    findNavController().navigate(R.id.action_deleteFragment_to_cartFragment)
                    dialog?.dismiss()

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().clearBackStack(R.id.deleteFragment)
                    findNavController().navigate(R.id.action_deleteFragment_to_cartFragment)
                    dialog?.dismiss()
                }
            }
        }

    }

}