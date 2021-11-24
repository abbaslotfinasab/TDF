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
import com.utechia.tdf.databinding.FragmentCancelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelFragment : DialogFragment() {

    private lateinit var binding: FragmentCancelBinding
    private val orderViewModel:OrderViewModel by viewModels()
    private var orderId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments !=null){
           orderId = requireArguments().getInt("orderId")

        }

        binding.btnKeep.setOnClickListener {
            dialog?.dismiss()
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            orderViewModel.cancelOrder(orderId)
            findNavController().clearBackStack(R.id.orderFragment)
            findNavController().navigate(R.id.action_cancelFragment_to_orderFragment)
            dialog?.dismiss()
        }



    }

}