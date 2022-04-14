package com.utechia.tdf.home.teaboy

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
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentDeactivationBinding
import com.utechia.tdf.order.teaboy.OrderCountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeactivationFragment : DialogFragment(),View.OnClickListener {

    private lateinit var binding: FragmentDeactivationBinding
    private val orderViewModel: OrderCountViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
    private var floorId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeactivationBinding.inflate(inflater, container, false)
        binding.btnDelete.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.exit.setOnClickListener(this)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments !=null){
            floorId = requireArguments().getInt("floorId",0)
        }

        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)

        observer()


    }

    private fun observer() {
        orderViewModel.orderModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)



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
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.btnCancel -> {
                findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
            }

            R.id.exit -> {
                findNavController().navigate(R.id.action_deactivationFragment_to_teaBoyHomeFragment)
            }

            R.id.btnDelete -> {

                orderViewModel.setStatus(false,floorId)
            }
        }
    }
}