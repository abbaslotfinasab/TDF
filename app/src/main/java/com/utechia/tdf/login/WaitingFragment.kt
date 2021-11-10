package com.utechia.tdf.login

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
import com.utechia.tdf.databinding.FragmentWaitingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitingFragment : DialogFragment() {

    private lateinit var binding: FragmentWaitingBinding
    private val verifyViewModel: VerifyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingBinding.inflate(inflater, container, false)

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

            val code = requireArguments().getString("code", "")
            verifyViewModel.verify(code)

        }

        observer()
    }

    private fun observer() {
        verifyViewModel.verifyModel.observe(viewLifecycleOwner){

            when(it){

                is Result.Success ->{
                    findNavController().navigate(R.id.action_waitingFragment_to_authenticationFragment)
                }

                is Result.Loading ->{}

                is Result.Error ->{
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            }
        }
    }
}