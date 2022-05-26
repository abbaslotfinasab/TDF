package com.utechia.tdf.reservation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.model.reservation.InviteDetailsModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReservationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class ReservationDetails : Fragment() {

    private lateinit var binding: FragmentReservationDetailsBinding
    private val reservationDetailsViewModel:ReservationDetailsViewModel by viewModels()
    private val inviteDetailsAdapter:InviteDetailsAdapter = InviteDetailsAdapter()
    private lateinit var prefs: SharedPreferences
    private var meetId:Int = 0
    private var reservation = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)


        if (arguments!=null) {
            meetId = requireArguments().getInt(ReservationEnum.ID.reservation,0)
            reservation = requireArguments().getString(ReservationEnum.None.reservation,"")
        }

        reservationDetailsViewModel.getSingleMeeting(meetId)

        binding.invitePeopleRecycler.apply {
            adapter = inviteDetailsAdapter
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false)
        }

        binding.btnBook.setOnClickListener {
            val bundle = bundleOf(ReservationEnum.ID.reservation to meetId)
            findNavController().navigate(R.id.action_reservationDetails_to_cancelReservationFragment,bundle)
        }

        observer()
    }

    private fun observer(){
        reservationDetailsViewModel.meeting.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.title.text = it.data.subject
                    binding.description.text = it.data.description
                    binding.dateLine.text = "${LocalDate.parse(it.data.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).dayOfWeek.name.lowercase().replaceFirstChar {it1 -> it1.uppercase()}}" +
                            " ${LocalDate.parse(it.data.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).dayOfMonth}, " +
                            "${LocalDate.parse(it.data.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).month.name.uppercase().subSequence(0,3)}, " +
                            "${LocalDate.parse(it.data.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).year}"

                    val hour = it.data.duration?.div(60)
                    val minute = it.data.duration?.rem(60)
                    when{
                        hour==0 ->{
                            binding.timeLine.text = "${LocalTime.parse(it.data.startsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} to ${LocalTime.parse(it.data.endsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} (${(it.data.duration?.rem(
                                60
                            ))}M)"
                        }
                        minute ==0 -> {
                            binding.timeLine.text = "${LocalTime.parse(it.data.startsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} to ${LocalTime.parse(it.data.endsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} (${(it.data.duration?.div(
                                60
                            ))}H)"                }
                        else ->{
                            binding.timeLine.text = "${LocalTime.parse(it.data.startsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} to ${LocalTime.parse(it.data.endsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} (${(it.data.duration?.div(
                                60
                            ))}H ${(it.data.duration?.rem(
                                60
                            ))}M)"
                        }
                    }

                    binding.location.text = "Floor ${it.data.room?.floor?.name}"
                    binding.roomName.text =  it.data.room?.name

                    if(it.data.status == ReservationEnum.Cancel.reservation || reservation == ReservationEnum.None.reservation){
                        binding.btnBook.visibility =  View.GONE
                    }else{
                        binding.btnBook.visibility =  View.VISIBLE

                    }
                    Glide.with(requireActivity())
                        .load(it.data.room?.coverPhoto)
                        .error(R.mipmap.meet_place)
                        .placeholder(R.mipmap.meet_place)
                        .centerCrop()
                        .into(binding.roomImage)

                    inviteDetailsAdapter.addData(InviteDetailsModel(it.data.presenter.name,it.data.presenter.jobTitle,it.data.presenter.mail,it.data.presenter.profilePictureModel?.url,ReservationEnum.Presenter.reservation))

                    it.data.attendees.map { it1 ->
                        if (it1.id != prefs.getInt(MainEnum.UserId.main,0))
                        inviteDetailsAdapter.addData(InviteDetailsModel(it1.name,it1.jobTitle,it1.mail,it1.profilePictureModel?.url,ReservationEnum.Attend.reservation))
                    }

                    it.data.guests.map { it1 ->
                        inviteDetailsAdapter.addData(InviteDetailsModel(it1.name,it1.job,it1.mail,null,ReservationEnum.Guest.reservation))
                    }
                    binding.inviteTitle.text = "People(${inviteDetailsAdapter.userList.size})"
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}