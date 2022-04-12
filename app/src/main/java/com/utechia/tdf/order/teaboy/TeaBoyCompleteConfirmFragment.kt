package com.utechia.tdf.order.teaboy

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
import com.utechia.tdf.databinding.FragmentAcceptBinding
import com.utechia.tdf.databinding.FragmentTeaBoyCompleteConfirmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyCompleteConfirmFragment : DialogFragment(),View.OnClickListener {

    private lateinit var binding: FragmentTeaBoyCompleteConfirmBinding
    private val teaBoyOrderViewModel: TeaBoyOrderDetailsViewModel by viewModels()
    private var orderId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyCompleteConfirmBinding.inflate(inflater, container, false)
        binding.exit.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.btnAccept.setOnClickListener(this)

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
            orderId = requireArguments().getInt(OrderEnum.ID.order, 0)

        observer()
    }

    private fun observer() {

        teaBoyOrderViewModel.orderModel.observe(viewLifecycleOwner) {


            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_completeFragment_to_orderFragment)
                    dialog?.dismiss()
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnAccept.isEnabled = false
                    binding.btnCancel.isEnabled = false
                    binding.exit.isEnabled = false

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_acceptFragment_to_teaBoyOrdersFragment)
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.exit ->{
                dialog?.dismiss()
            }

            R.id.btnCancel ->{
                dialog?.dismiss()
            }

            R.id.btnAccept ->{
                teaBoyOrderViewModel.deliverOrder(orderId)
            }
        }
    }
}