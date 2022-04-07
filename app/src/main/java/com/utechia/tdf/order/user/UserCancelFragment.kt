package com.utechia.tdf.order.user

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
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCancelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserCancelFragment : DialogFragment(),View.OnClickListener {

    private lateinit var binding: FragmentCancelBinding
    private val userOrderViewModel: UserOrderDetailsViewModel by viewModels()
    private lateinit var bundle: Bundle
    private var orderId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelBinding.inflate(inflater, container, false)
        binding.btnCancel.setOnClickListener(this)
        binding.btnKeep.setOnClickListener(this)
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
        bundle = bundleOf(OrderEnum.Type.order to OrderEnum.Cancel.order)

        observer()


        if (arguments != null) {
            orderId = requireArguments().getInt(OrderEnum.ID.order)

        }

    }

    private fun observer() {

        userOrderViewModel.userOrderModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(
                        R.id.action_cancelFragment_to_orderFragment,
                        bundle
                    )
                    dialog?.dismiss()

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnKeep.isEnabled = false
                    binding.btnCancel.isEnabled = false
                    binding.exit.isEnabled = false


                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.action_cancelFragment_to_orderFragment,
                        bundle
                    )
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

            R.id.btnKeep ->{
                dialog?.dismiss()
            }

            R.id.btnCancel ->{
                userOrderViewModel.cancelOrder(orderId)
            }
        }
    }
}