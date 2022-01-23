package com.utechia.tdf.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentEventDetailsBinding
import com.utechia.tdf.databinding.FragmentEventSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.bringToFront()

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }


        Glide.with(requireActivity())
            .load(R.mipmap.ic_evente_banner_foreground)
            .centerCrop()
            .error(R.mipmap.ic_evente_banner_foreground)
            .into(binding.eventImage)


    }

}