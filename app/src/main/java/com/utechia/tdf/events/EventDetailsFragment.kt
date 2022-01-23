package com.utechia.tdf.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentEventDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private val eventDetViewModel:EventDetailsViewModel by viewModels()
    private var eId = 0
    private var timeZone = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null){
            eId = requireArguments().getInt("eId",0)
        }

        eventDetViewModel.getEvent(eId)

        binding.backArrow.bringToFront()

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        observer()
    }

    private fun observer() {

        eventDetViewModel.event.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    Glide.with(requireActivity())
                        .load("https://sandbox.tdf.gov.sa/${it.data.coverphoto}")
                        .centerCrop()
                        .error(R.mipmap.ic_evente_banner_foreground)
                        .into(binding.eventImage)

                    binding.title.text = it.data.title
                    binding.type.text = it.data.type
                    binding.capacity.text = it.data.capacity.toString()
                    timeZone = OffsetDateTime.parse(it.data.datestart).atZoneSameInstant(
                        ZoneId.systemDefault()
                    ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm"))

                    binding.date.text = "$timeZone"
                    binding.time.text = "${it.data.duration} hrs"

                    binding.description.text = it.data.description
                    binding.location.text = it.data.eventPlace


                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}