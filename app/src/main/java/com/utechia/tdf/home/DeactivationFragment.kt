package com.utechia.tdf.home

import android.content.Context
import android.content.SharedPreferences
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
import com.utechia.tdf.databinding.FragmentDeactivationBinding
import com.utechia.tdf.order.OrderCountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeactivationFragment : DialogFragment() {

    private lateinit var binding: FragmentDeactivationBinding
    private val orderViewModel: OrderCountViewModel by viewModels()
    private lateinit var prefs: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeactivationBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)

        binding.btnCancel.setOnClickListener {
            findNavController().clearBackStack(R.id.teaBoyHomeFragment)
            findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
            dialog?.dismiss()
        }

        binding.exit.setOnClickListener {
            findNavController().clearBackStack(R.id.teaBoyHomeFragment)
            findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
            dialog?.dismiss()
        }

        binding.btnDelete.setOnClickListener {
            orderViewModel.setStatus(false)
            observer()
            with(prefs.edit()) {
                putBoolean("isTeaBoyActive", false)
            }.apply()
        }
    }

    private fun observer() {
        orderViewModel.orderModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().clearBackStack(R.id.teaBoyHomeFragment)
                    findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
                    dialog?.dismiss()


                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnCancel.isEnabled = false
                    binding.btnDelete.isEnabled = false
                    binding.exit.isEnabled = false

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().clearBackStack(R.id.teaBoyHomeFragment)
                    findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
                    dialog?.dismiss()
                }
            }
        }
    }

}