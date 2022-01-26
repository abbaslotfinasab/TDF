package com.utechia.tdf.order

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentRateConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateConfirmationFragment : DialogFragment() {

    private lateinit var binding: FragmentRateConfirmationBinding
    private val orderRateVieModel:OrderRateViwModel by viewModels()
    private lateinit var bundle: Bundle
    private var orderId = 0
    private var rate = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRateConfirmationBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = bundleOf("type" to "rate")

        observer()


        if (arguments != null) {
            orderId = requireArguments().getInt("orderId")
            rate = requireArguments().getInt("rate")

        }
        binding.btnKeep.setOnClickListener {
            orderRateVieModel.setRate(orderId,rate)
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()

        }
    }

    private fun observer() {
        orderRateVieModel.orderModel.observe(viewLifecycleOwner){


            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.rateConfirmationFragment_to_OrderFragment,bundle)
                    dialog?.dismiss()

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.rateConfirmationFragment_to_OrderFragment,bundle)
                    dialog?.dismiss()


                }
            }
        }
    }
}