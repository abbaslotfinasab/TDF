package com.utechia.tdf.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private var image = 0
    private var name = ""
    private var cal = 0
    private var time = ""
    private var number = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null){
            image = requireArguments().getInt("image",0)
            name = requireArguments().getString("name","")
            cal = requireArguments().getInt("cal",0)
            time = requireArguments().getString("time","")
            number = requireArguments().getInt("number",0)
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
        binding.numberText.text = number.toString()

        binding.plusNumber.setOnClickListener {
            number +=1
            binding.numberText.text = number.toString()
        }

        binding.minusNumber.setOnClickListener {

            if (number>0) {
                number -=1
                binding.numberText.text = number.toString()
            }

        }


    }

}