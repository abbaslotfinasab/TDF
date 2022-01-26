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
import com.utechia.tdf.databinding.FragmentActivationBinding
import com.utechia.tdf.order.OrderCountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivationFragment : DialogFragment(),View.OnClickListener{

    private lateinit var binding: FragmentActivationBinding
    private val orderViewModel: OrderCountViewModel by viewModels()
    private lateinit var prefs: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivationBinding.inflate(inflater, container, false)

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

        observer()

    }

    private fun observer() {
        orderViewModel.orderModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_activationFragment_to_teaBoyHomeFragment)

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
                    findNavController().navigate(R.id.action_activationFragment_to_teaBoyHomeFragment)
                }
            }
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.btnCancel -> {
                dialog?.dismiss()
            }

            R.id.exit -> {
                dialog?.dismiss()
            }

            R.id.btnDelete -> {

                orderViewModel.setStatus(true)
                with(prefs.edit()) {
                    putBoolean("isTeaBoyActive", true)
                }.apply()
            }
        }
    }
}