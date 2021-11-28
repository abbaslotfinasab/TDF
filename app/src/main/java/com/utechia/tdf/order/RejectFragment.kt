package com.utechia.tdf.order

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
import com.utechia.tdf.databinding.FragmentRejectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RejectFragment : DialogFragment() {

    private lateinit var binding: FragmentRejectBinding
    private val orderViewModel: OrderViewModel by viewModels()
    private var orderId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRejectBinding.inflate(inflater, container, false)

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
            orderId = requireArguments().getInt("orderId", 0)

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnDelete.setOnClickListener {
            orderViewModel.rejectOrder(orderId)
            findNavController().clearBackStack(R.id.teaBoyOrdersFragment)
            findNavController().navigate(R.id.action_rejectFragment_to_teaBoyOrdersFragment)
            dialog?.dismiss()
        }





    }

}