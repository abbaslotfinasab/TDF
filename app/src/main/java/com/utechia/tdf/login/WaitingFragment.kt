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
import com.utechia.tdf.databinding.FragmentWaitingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitingFragment : DialogFragment() {

    private lateinit var binding: FragmentWaitingBinding
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null) {

            token = requireArguments().getString("token", "")
            Toast.makeText(context,token,Toast.LENGTH_SHORT).show()

        }


    }

}