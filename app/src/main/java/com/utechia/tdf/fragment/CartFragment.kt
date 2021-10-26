package com.utechia.tdf.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : DialogFragment() {

    private lateinit var binding: FragmentCartBinding

    private var image = 0
    private var name = ""
    private var cal = 0
    private var time = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null){
            image = requireArguments().getInt("image",0)
            name = requireArguments().getString("name","")
            cal = requireArguments().getInt("cal",0)
            time = requireArguments().getString("time","")
        }

        Glide.with(requireActivity())
            .load(
                if (image==0)
                    R.mipmap.image1
                else
                    R.mipmap.image2
            )
            .into(binding.image)

        binding.name.text = name
        binding.cal.text = cal.toString()+"cal"
        binding.time.text = time


    }

}