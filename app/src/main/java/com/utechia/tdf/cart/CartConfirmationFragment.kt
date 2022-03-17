package com.utechia.tdf.cart

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
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCartConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartConfirmationFragment : DialogFragment(),View.OnClickListener {

    private lateinit var binding: FragmentCartConfirmationBinding
    private val cartViewModel: CartViewModel by viewModels()
    private var location = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartConfirmationBinding.inflate(inflater, container, false)

        binding.btnAccept.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.exit.setOnClickListener(this)

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
            location = requireArguments().getString(MainEnum.Location.main).toString()
        }


        observer()

    }

    private fun observer() {

        cartViewModel.cartModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_cartConfirmationFragment_to_userOrderFragment)
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
                    dialog?.dismiss()
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

            R.id.btnAccept -> {
                cartViewModel.checkoutCart(location)
            }
        }
    }
}