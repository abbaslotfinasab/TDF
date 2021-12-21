package com.utechia.tdf.order

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentConfirmSurveyBinding
import com.utechia.tdf.databinding.FragmentRateConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import org.json.JSONArray


@AndroidEntryPoint
class RateConfirmationFragment : DialogFragment() {

    private lateinit var binding: FragmentRateConfirmationBinding
    private val orderRateVieModel:OrderRateViwModel by viewModels()
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

        if (arguments != null) {
            orderId = requireArguments().getInt("orderId")
            rate = requireArguments().getInt("rate")

        }
        binding.btnKeep.setOnClickListener {
            orderRateVieModel.setRate(orderId,rate)
            observer()


        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.rateConfirmationFragment_to_OrderFragment)
            dialog?.dismiss()

        }
    }

    private fun observer() {
        orderRateVieModel.orderModel.observe(viewLifecycleOwner){


            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.rateConfirmationFragment_to_OrderFragment)


                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.rateConfirmationFragment_to_OrderFragment)

                }
            }
        }
    }
}