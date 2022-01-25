package com.utechia.tdf.events

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentEventDetailsBinding
import com.utechia.tdf.ticket.ChatItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private val guestAdapter:GuestAdapter = GuestAdapter()
    private val eventDetViewModel:EventDetailsViewModel by viewModels()
    private val eventViewModel:EventViewModel by viewModels()
    private var eId = 0
    private var contributeId = 0
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
                    guestAdapter.addData(it.data.guestModels)

                    binding.recyclerView.apply {
                        adapter = guestAdapter
                        layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        if(itemDecorationCount>0)
                            removeItemDecorationAt(0)
                        addItemDecoration(GuestItemDecoration())

                    }

                    Glide.with(requireActivity())
                        .load("https://sandbox.tdf.gov.sa${it.data.coverphoto}")
                        .centerCrop()
                        .error(R.mipmap.ic_evente_banner_foreground)
                        .into(binding.eventImage)

                    binding.title.text = it.data.title
                    binding.type.text = it.data.type
                    binding.capacity.text = "${it.data.joinnumbr}/${it.data.capacity.toString()}"
                    timeZone = OffsetDateTime.parse(it.data.datestart).atZoneSameInstant(
                        ZoneId.systemDefault()
                    ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm"))

                    binding.date.text = "$timeZone"
                    binding.time.text = "${it.data.duration} min"

                    binding.description.text = it.data.description
                    binding.location.text = it.data.eventPlace

                    when(it.data.status){

                        "End" -> {
                            binding.appCompatButton.visibility = View.GONE
                        }

                        "Inprogress" -> {
                            binding.appCompatButton.visibility = View.GONE
                        }
                        "Upcoming" -> {
                            when(it.data.contribute){

                                null ->{
                                    binding.appCompatButton.apply {
                                        visibility = View.VISIBLE
                                        text = "Apply"
                                        setBackgroundColor(Color.parseColor("#3360DD"))
                                        isEnabled = true
                                        setOnClickListener{
                                            eventViewModel.applyEvent(eId)
                                            applyObserver()
                                        }
                                    }

                                }

                                "Rejected" -> {
                                    binding.appCompatButton.apply {
                                        visibility = View.VISIBLE
                                        text = "Apply"
                                        setBackgroundColor(Color.parseColor("#3360DD"))
                                        isEnabled = true
                                        setOnClickListener{
                                            eventViewModel.applyEvent(eId)
                                            applyObserver()
                                        }
                                    }
                                }

                                "Cancelled" -> {
                                    binding.appCompatButton.apply {
                                        visibility = View.VISIBLE
                                        text = "Apply"
                                        setBackgroundColor(Color.parseColor("#3360DD"))
                                        isEnabled = true
                                        setOnClickListener{
                                            eventViewModel.applyEvent(eId)
                                            applyObserver()
                                        }
                                    }
                                }

                                "Pending" -> {
                                    binding.appCompatButton.apply {
                                        contributeId = it.data.contributeId!!
                                        visibility = View.VISIBLE
                                        text = "Cancel"
                                        setBackgroundColor(Color.parseColor("#FF6464"))
                                        isEnabled = true
                                        setOnClickListener {
                                            val bundle = bundleOf("eId" to contributeId)
                                            findNavController().navigate(R.id.action_eventDetailsFragment_to_cancelEventFragment,bundle)
                                        }
                                    }
                                }

                                "Attending" -> {
                                    binding.appCompatButton.apply {
                                        contributeId = it.data.contributeId!!
                                        visibility = View.VISIBLE
                                        text = "Cancel"
                                        setBackgroundColor(Color.parseColor("#FF6464"))
                                        isEnabled = true
                                        setOnClickListener {
                                            val bundle = bundleOf("eId" to contributeId)
                                            findNavController().navigate(R.id.action_eventDetailsFragment_to_cancelEventFragment,bundle)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.appCompatButton.visibility = View.GONE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.appCompatButton.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun applyObserver() {
        eventViewModel.event.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_eventDetailsFragment_to_eventResultFragment)

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