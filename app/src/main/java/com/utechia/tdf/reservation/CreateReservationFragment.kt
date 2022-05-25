package com.utechia.tdf.reservation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.model.reservation.AnswerReservationModel
import com.utechia.domain.model.reservation.DateModel
import com.utechia.domain.model.reservation.GuestsModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter



@AndroidEntryPoint
class CreateReservationFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentCreateReservationBinding
    private val invitePeopleAdapter:InvitePeopleAdapter = InvitePeopleAdapter()
    private val inviteGuestAdapter:InviteGuestAdapter = InviteGuestAdapter()
    private val reservationDateAdapter:ReservationDateAdapter = ReservationDateAdapter(this)
    private val reservationTimeAdapter:ReservationTimeAdapter = ReservationTimeAdapter()
    private val reservationViewModel: ReservationViewModel by viewModels()
    private val timeViewModel:TimeViewModel by viewModels()
    private var date = ""
    private val dateModel:MutableList<DateModel> = mutableListOf()
    private var name = ""
    private var number = ""
    private var title = ""
    private var cover = ""
    private var roomId = -1
    private var reservationDate = ""
    private var reservationDayOfWeek = ""
    private var startTime = ""
    private var endTime = ""
    private var guests:MutableList<GuestsModel> = mutableListOf()
    private var attendees:MutableSet<Int> = mutableSetOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        with(binding){
            listOf(
                btnAdd,btnRoom,btnMore,btnSelect,btnCalendarSelected,btnCalendarUnSelected,btnBook
            ).forEach{button ->
                button.setOnClickListener{view -> onClick(view)}
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            title = requireArguments().getString(ReservationEnum.Title.reservation, "")
            cover = requireArguments().getString(ReservationEnum.Cover.reservation, "")
            roomId = requireArguments().getInt(ReservationEnum.ID.reservation, 0)
            reservationDate = requireArguments().getString(ReservationEnum.Date.reservation, "")
            reservationDayOfWeek = requireArguments().getString(ReservationEnum.DayOfWeek.reservation, "")
        }

        calculateDays()

        binding.dateRecycler.apply {
            adapter = reservationDateAdapter
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(DateItemDecoration())
        }
        reservationDateAdapter.addData(dateModel)

        binding.timeRecycler.apply {
            adapter = reservationTimeAdapter
            layoutManager = GridLayoutManager(requireActivity(),6)
        }

        binding.invitePeopleRecycler.apply {
            adapter = invitePeopleAdapter
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false)
        }

        binding.inviteGuestPeopleRecycler.apply {
            adapter = inviteGuestAdapter
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false)
        }

        selectRoom()
        setDate()
        setTime()
        invitePeople()
        roomObserver()
        timeObserver()
        bookReservation()
    }

    private fun bookReservation(){
        BookListener.bookListener.observe(viewLifecycleOwner){
            if (it==true){
                invitePeopleAdapter.userList.map { it1->
                    it1.id?.let { it2 -> attendees.add(it2) }
                }
                inviteGuestAdapter.userList.map {it1 ->
                    guests.add(GuestsModel(it1.name,it1.mail,it1.jobTitle))
                }
                reservationViewModel.createMeeting(AnswerReservationModel(binding.titleInput.text.toString(),binding.noteInput.text.toString(),reservationDate,startTime,endTime,
                    attendees.toMutableList(),guests,roomId))
            }
        }
    }

    private fun roomObserver() {
        reservationViewModel.reservationModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_createReservationFragment_to_reservationFragment)
                }

                is Result.Loading -> {}

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun timeObserver() {
        timeViewModel.timeModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    if (it.data.size!=0) {
                        binding.reservationTimeLayout.visibility = View.VISIBLE
                        reservationTimeAdapter.addData(it.data)
                    }
                    else{
                        binding.reservationTimeLayout.visibility = View.GONE
                    }
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    binding.reservationTimeLayout.visibility = View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun invitePeople(){
        InvitationListener.addInvitationListener.observe(viewLifecycleOwner) {
            if (it != null) {
                it.id?.let { it1 -> reservationViewModel.addGuess(it1) }
                invitePeopleAdapter.addData(it)
            }else {
                invitePeopleAdapter.userList.clear()
                invitePeopleAdapter.notifyDataSetChanged()
            }
            checkLayout()
        }
        InvitationListener.removeInvitationListener.observe(viewLifecycleOwner){
            it.id?.let { it1 -> reservationViewModel.removeGuess(it1) }

            for(i in 0 until invitePeopleAdapter.userList.size )
                if (invitePeopleAdapter.userList[i].id == it.id){
                    invitePeopleAdapter.userList.removeAt(i)
                    invitePeopleAdapter.notifyItemRemoved(i)
                    break
            }
            checkLayout()
        }
        InvitationListener.addGuestListener.observe(viewLifecycleOwner){
            if (it !=null) {
                it.id?.let { it1 -> reservationViewModel.addGuess(it1) }
                inviteGuestAdapter.addData(it)
                checkLayout()
            }else{
                inviteGuestAdapter.userList.clear()
                inviteGuestAdapter.notifyDataSetChanged()
            }
            checkLayout()
        }
        InvitationListener.removeGuestListener.observe(viewLifecycleOwner){
            checkLayout()
        }
    }

    private fun setDate(){

        DateListener.dateAdapterListener.observe(viewLifecycleOwner){

            if (it !=null) {
                binding.dateTitle.text = "${
                    it.name?.lowercase()?.replaceFirstChar { it1 ->
                        it1.uppercase()
                    }
                }  ${LocalDate.parse(it.date,DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"
                reservationDate = it.date.toString()
                binding.btnCalendarSelected.visibility = View.INVISIBLE
                binding.btnCalendarUnSelected.visibility = View.VISIBLE

                if (reservationDate.isNotEmpty() && roomId != -1){
                    timeViewModel.getMeetingTime(reservationDate, roomId)
                }
            }else{
                binding.dateTitle.text = ""
                reservationDate = ""
                reservationDateAdapter.previousIndex = -1
                reservationDateAdapter.notifyDataSetChanged()
                binding.btnCalendarSelected.visibility = View.INVISIBLE
                binding.btnCalendarUnSelected.visibility = View.VISIBLE
            }

        }
        DateListener.datePickerListener.observe(viewLifecycleOwner) {
            if (it != null) {

                binding.dateTitle.text = "${
                    it.name?.lowercase()?.replaceFirstChar { it1 ->
                        it1.uppercase()
                    }
                }  ${LocalDate.parse(it.date,DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"

                reservationDate = it.date.toString()
                reservationDateAdapter.previousIndex = -1
                reservationDateAdapter.notifyDataSetChanged()
                binding.btnCalendarSelected.visibility = View.VISIBLE
                binding.btnCalendarUnSelected.visibility = View.INVISIBLE

                if (reservationDate.isNotEmpty() && roomId != -1) {
                    timeViewModel.getMeetingTime(reservationDate, roomId)
                }
            }
            else{
                binding.dateTitle.text = ""
                reservationDate = ""
                reservationDateAdapter.previousIndex = -1
                reservationDateAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setTime(){
        TimeListener.timeListener.observe(viewLifecycleOwner){
            startTime = it.elementAt(0).start.toString()
            endTime = it.elementAt(0).end.toString()
            it.forEach { duration ->
                if (LocalTime.parse(duration.start, DateTimeFormatter.ofPattern("HH:mm")).toSecondOfDay()<LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm")).toSecondOfDay())
                    startTime = duration.start.toString()
            }
            it.forEach { duration ->
                if (LocalTime.parse(duration.end, DateTimeFormatter.ofPattern("HH:mm")).toSecondOfDay()>LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm")).toSecondOfDay())
                    endTime = duration.end.toString()
            }
            val delay : Duration = Duration.between(LocalTime.parse(startTime,DateTimeFormatter.ofPattern("HH:mm")),LocalTime.parse(endTime,DateTimeFormatter.ofPattern("HH:mm")))

            binding.timeTitle.text = "$startTime - $endTime  (${(delay.toMinutes().div(
                60
            ))}H ${(delay.toMinutes().rem(
                60
            ))}M)"
        }
    }

    private fun selectRoom(){

        RoomListener.roomListener.observe(viewLifecycleOwner) {
            if (it != null) {

                binding.title.setTextColor(Color.WHITE)
                binding.btnSelect.visibility = View.GONE
                binding.imageRoom.visibility = View.VISIBLE
                binding.roomTitle.visibility = View.VISIBLE
                binding.btnRoom.visibility = View.VISIBLE
                binding.roomTitle.text = it.name
                binding.capacityImage.visibility = View.VISIBLE
                binding.capacityRoom.visibility = View.VISIBLE
                binding.capacityRoom.text = "capacity: ${it.capacity}"

                Glide.with(requireContext())
                    .load(it.coverPhoto)
                    .error(R.mipmap.meet_place_blur)
                    .centerCrop()
                    .transform(BlurTransformation(10, 2))
                    .into(binding.imageRoom)
                    .onLoadStarted(ContextCompat.getDrawable(requireContext(),R.mipmap.meet_place_blur))


                roomId = it.id?:-1

                if (reservationDate.isNotEmpty() && roomId != -1) {
                    timeViewModel.getMeetingTime(reservationDate, roomId)
                }
            }else{
                binding.title.setTextColor(Color.BLACK)
                binding.btnSelect.visibility = View.VISIBLE
                binding.imageRoom.visibility = View.GONE
                binding.roomTitle.visibility = View.GONE
                binding.btnRoom.visibility = View.GONE
                binding.capacityRoom.visibility = View.GONE
                binding.capacityImage.visibility = View.GONE

                roomId = -1

            }
        }
    }

    private fun calculateDays(){
        dateModel.clear()
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        number = LocalDateTime.now().dayOfMonth.toString()
        name = LocalDateTime.now().dayOfWeek.name
        dateModel.add(DateModel(0,number,name,date))

        for (i in 1 until  6) {
            date = LocalDateTime.now().plusDays(i.toLong()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            number = LocalDateTime.now().plusDays(i.toLong()).dayOfMonth.toString()
            name = LocalDateTime.now().plusDays(i.toLong()).dayOfWeek.name
            dateModel.add(DateModel(i,number,name, date))
        }
    }
    private fun checkLayout(){
        when {
            invitePeopleAdapter.userList.size == 0 && inviteGuestAdapter.userList.size == 0 -> {
                binding.invitePeopleRecycler.visibility = View.GONE
                binding.inviteGuestPeopleRecycler.visibility = View.GONE
                binding.guessTitle.visibility = View.GONE
                binding.emptyTitle.visibility = View.VISIBLE
                binding.btnAdd.visibility = View.VISIBLE
                binding.btnMore.visibility = View.GONE
            }

            inviteGuestAdapter.userList.size == 0 -> {
                binding.invitePeopleRecycler.visibility = View.VISIBLE
                binding.inviteGuestPeopleRecycler.visibility = View.GONE
                binding.guessTitle.visibility = View.GONE
                binding.emptyTitle.visibility = View.GONE
                binding.btnAdd.visibility = View.GONE
                binding.btnMore.visibility = View.VISIBLE
            }
            else -> {
                binding.invitePeopleRecycler.visibility = View.VISIBLE
                binding.inviteGuestPeopleRecycler.visibility = View.VISIBLE
                binding.guessTitle.visibility = View.VISIBLE
                binding.emptyTitle.visibility = View.GONE
                binding.btnAdd.visibility = View.GONE
                binding.btnMore.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnSelect -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_roomListFragment)
            }
            binding.btnRoom -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_roomListFragment)
            }
            binding.btnCalendarSelected -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_datePickerFragment)
            }
            binding.btnCalendarUnSelected -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_datePickerFragment)
            }
            binding.btnAdd -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_invitePeopleFragmente)
            }
            binding.btnMore -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_invitePeopleFragmente)
            }
            binding.btnBook -> {
                when{
                    roomId == -1 ->{
                        Toast.makeText(context,"No room selected",Toast.LENGTH_SHORT).show()
                    }

                    reservationDate == "" -> {
                        Toast.makeText(context,"No date selected",Toast.LENGTH_SHORT).show()
                    }
                    startTime == "" -> {
                        Toast.makeText(context,"No time selected",Toast.LENGTH_SHORT).show()
                    }
                    endTime == "" -> {
                        Toast.makeText(context,"No time selected",Toast.LENGTH_SHORT).show()
                    }
                    invitePeopleAdapter.userList.size==0 -> {
                        Toast.makeText(context,"No people invited",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        findNavController().navigate(R.id.action_createReservationFragment_to_reservationBookConfirmationFragment)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        reservationViewModel.deleteAll()
        RoomListener.roomListener.postValue(null)
        DateListener.dateAdapterListener.postValue(null)
        DateListener.datePickerListener.postValue(null)
        InvitationListener.addInvitationListener.postValue(null)
        InvitationListener.addGuestListener.postValue(null)
        BookListener.bookListener.postValue(null)
    }
}

