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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentActivationBinding
import com.utechia.tdf.databinding.FragmentConfirmBinding
import com.utechia.tdf.databinding.FragmentDeactivationBinding
import com.utechia.tdf.order.OrderCountViewModel
import com.utechia.tdf.refreshment.CartViewModel
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
            with(prefs.edit()) {
                putBoolean("isTeaBoyActive", false)
            }.apply()
            findNavController().clearBackStack(R.id.teaBoyHomeFragment)
            findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
            dialog?.dismiss()
        }
    }
}