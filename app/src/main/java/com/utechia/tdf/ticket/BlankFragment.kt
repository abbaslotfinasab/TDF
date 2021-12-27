package com.utechia.tdf.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    private var uri = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments !=null){
            uri = requireArguments().getString("uri","")

        }

        Glide.with(requireActivity())
            .load(uri)
            .error(R.drawable.ic_empty_upload)
            .into(binding.image)

    }
}