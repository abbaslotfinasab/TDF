package com.utechia.tdf.events


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.utechia.domain.enum.EventsEnum
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
    private val guestAdapter:GuestAdapter = GuestAdapter()
    private val eventDetailsViewModel:EventDetailsViewModel by viewModels()
    private lateinit var bundle:Bundle
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
            eId = requireArguments().getInt(EventsEnum.ID.event ,0)
        }

        eventDetailsViewModel.getEvent(eId)

        binding.appBarLayout.addOnOffsetChangedListener(object :AppBarLayout.OnOffsetChangedListener{

            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

                if (scrollRange == -1) {
                    if (appBarLayout != null) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                } else if (isShow) {
                    isShow = false
                }
            }
        })

        binding.toolbar.setNavigationIcon(R.drawable.ic_back)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        observer()
    }

    private fun observer() {

        eventDetailsViewModel.event.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    guestAdapter.addData(it.data[0].guestModels)

                    binding.recyclerView.apply {
                        adapter = guestAdapter
                        layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        if(itemDecorationCount>0)
                            removeItemDecorationAt(0)
                        addItemDecoration(GuestItemDecoration())

                    }

                    Glide.with(requireActivity())
                        .load(it.data[0].coverphoto)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_evente_banner_foreground)
                        .error(R.mipmap.ic_evente_banner_foreground)
                        .into(binding.eventImage)

                    binding.title.text = it.data[0].title
                    binding.type.text = it.data[0].type
                    binding.capacity.text = "${it.data[0].joinnumbr}/${it.data[0].capacity.toString()}"
                    timeZone = OffsetDateTime.parse(it.data[0].datestart).atZoneSameInstant(
                        ZoneId.systemDefault()
                    ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm"))

                    binding.date.text = timeZone
                    binding.time.text = "${it.data[0].duration} min"

                    binding.description.text = it.data[0].description
                    binding.location.text = it.data[0].eventPlace


                    if(it.data[0].isPublic==true) {
                        binding.appCompatButton.visibility = View.GONE
                        binding.status.visibility = View.VISIBLE

                        if(it.data[0].userrate == null && it.data[0].status == EventsEnum.End.event){
                            binding.appCompatButton.apply {
                                visibility = View.VISIBLE
                                text = resources.getText(R.string.evaluate)
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.confirm
                                    )
                                )
                                isEnabled = false
                            }
                            bundle = bundleOf(EventsEnum.ID.event to eId)
                            findNavController().navigate(
                                R.id.action_eventDetailsFragment_to_eventRateFragment,
                                bundle
                            )
                        }else if(it.data[0].userrate != null && it.data[0].status == EventsEnum.End.event){
                            binding.appCompatButton.apply {
                                visibility = View.VISIBLE
                                text = resources.getText(R.string.evaluate)
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.disActive
                                    )
                                )
                                isEnabled = false
                            }
                        }else{
                            binding.appCompatButton.visibility = View.GONE
                        }

                    }
                    else {

                        binding.status.visibility = View.GONE

                        when (it.data[0].status) {

                            EventsEnum.End.event -> {

                                when (it.data[0].contribute) {

                                    EventsEnum.Attending.event -> {
                                        if (it.data[0].userrate != null) {
                                            binding.appCompatButton.apply {
                                                visibility = View.VISIBLE
                                                text = resources.getText(R.string.evaluate)
                                                setBackgroundColor(
                                                    ContextCompat.getColor(
                                                        context,
                                                        R.color.disActive
                                                    )
                                                )
                                                isEnabled = false
                                            }
                                        } else {
                                            binding.appCompatButton.apply {
                                                visibility = View.VISIBLE
                                                text = resources.getText(R.string.evaluate)
                                                setBackgroundColor(
                                                    ContextCompat.getColor(
                                                        context,
                                                        R.color.confirm
                                                    )
                                                )
                                                isEnabled = false
                                            }
                                            bundle = bundleOf(EventsEnum.ID.event to eId)
                                            findNavController().navigate(
                                                R.id.action_eventDetailsFragment_to_eventRateFragment,
                                                bundle
                                            )
                                        }
                                    }
                                    else ->
                                        binding.appCompatButton.visibility = View.GONE
                                }
                            }

                            EventsEnum.Inprogress.event -> {
                                binding.appCompatButton.visibility = View.GONE
                            }
                            EventsEnum.Upcoming.event -> {
                                when (it.data[0].contribute) {

                                    null -> {
                                        binding.appCompatButton.apply {
                                            visibility = View.VISIBLE
                                            text = resources.getText(R.string.apply)
                                            setBackgroundColor(
                                                ContextCompat.getColor(
                                                    context,
                                                    R.color.confirm
                                                )
                                            )
                                            isEnabled = true
                                            setOnClickListener {
                                                eventDetailsViewModel.applyEvent(eId)
                                                applyObserver()
                                            }
                                        }

                                    }

                                    EventsEnum.Rejected.event -> {
                                        binding.appCompatButton.apply {
                                            visibility = View.VISIBLE
                                            text = resources.getText(R.string.apply)
                                            setBackgroundColor(
                                                ContextCompat.getColor(
                                                    context,
                                                    R.color.confirm
                                                )
                                            )
                                            isEnabled = true
                                            setOnClickListener {
                                                eventDetailsViewModel.applyEvent(eId)
                                                applyObserver()
                                            }
                                        }
                                    }

                                    EventsEnum.Cancelled.event -> {
                                        binding.appCompatButton.apply {
                                            visibility = View.VISIBLE
                                            text = resources.getText(R.string.apply)
                                            setBackgroundColor(
                                                ContextCompat.getColor(
                                                    context,
                                                    R.color.confirm
                                                )
                                            )
                                            isEnabled = true
                                            setOnClickListener {
                                                eventDetailsViewModel.applyEvent(eId)
                                                applyObserver()
                                            }
                                        }
                                    }

                                    EventsEnum.Pending.event -> {
                                        binding.appCompatButton.apply {
                                            contributeId = it.data[0].contributeId!!
                                            visibility = View.VISIBLE
                                            text = resources.getText(R.string.cancel)
                                            setBackgroundColor(
                                                ContextCompat.getColor(
                                                    context,
                                                    R.color.bubble
                                                )
                                            )
                                            isEnabled = true
                                            setOnClickListener {
                                                val bundle =
                                                    bundleOf(EventsEnum.ID.event to contributeId)
                                                findNavController().navigate(
                                                    R.id.action_eventDetailsFragment_to_cancelEventFragment,
                                                    bundle
                                                )
                                            }
                                        }
                                    }

                                    EventsEnum.Attending.event -> {
                                        binding.appCompatButton.apply {
                                            contributeId = it.data[0].contributeId!!
                                            visibility = View.VISIBLE
                                            text = resources.getText(R.string.cancel)
                                            setBackgroundColor(
                                                ContextCompat.getColor(
                                                    context,
                                                    R.color.bubble
                                                )
                                            )
                                            isEnabled = true
                                            setOnClickListener {
                                                val bundle =
                                                    bundleOf(EventsEnum.ID.event to contributeId)
                                                findNavController().navigate(
                                                    R.id.action_eventDetailsFragment_to_cancelEventFragment,
                                                    bundle
                                                )
                                            }
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
        eventDetailsViewModel.event.observe(viewLifecycleOwner){

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