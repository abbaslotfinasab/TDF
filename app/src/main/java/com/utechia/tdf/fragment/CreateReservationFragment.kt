package com.utechia.tdf.fragment
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.domain.moodel.RoomModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.adapter.CreateReservationAdapter
import com.utechia.tdf.adapter.DatePickerAdapter
import com.utechia.tdf.adapter.TimePickerAdapter
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import com.utechia.tdf.utile.ItemDecoration
import com.utechia.tdf.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateReservationFragment : Fragment() {

    private lateinit var binding: FragmentCreateReservationBinding
    private val roomViewModel: RoomViewModel by viewModels()

    private var createReservationAdapter: CreateReservationAdapter = CreateReservationAdapter()
    private var datePickerAdapter:DatePickerAdapter = DatePickerAdapter()
    private lateinit var timePickerAdapter:TimePickerAdapter

    private var sdf = SimpleDateFormat("MM")
    private val mDate:Date = Date()
    private var currentMonth = sdf.format(mDate).toInt()

    private var _sdf = SimpleDateFormat("dd")
    private val dDate:Date = Date()
    private var currentDay = _sdf.format(dDate).toInt()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            currentMonth = requireArguments().getInt("day_id",currentMonth)
            currentDay = requireArguments().getInt("day_id", currentDay)
        }

        val main: ConstraintLayout = activity?.findViewById(R.id.mainLayout)!!
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val notificationIcon: ImageView = requireActivity().findViewById(R.id.notification)
        val custom: ImageView = requireActivity().findViewById(R.id.customButton)
        val menu: ImageView = requireActivity().findViewById(R.id.menu)
        val back: ConstraintLayout = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)
        navBar.visibility = View.GONE
        notificationIcon.isEnabled = false
        custom.visibility = View.GONE
        menu.visibility = View.INVISIBLE
        back.visibility = View.VISIBLE
        name.visibility = View.GONE
        title.visibility = View.VISIBLE
        subTitle.visibility = View.VISIBLE

        main.background = ColorDrawable(resources.getColor(R.color.white))

        binding.btnConfirm.bringToFront()

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        roomViewModel.getRoom(currentDay, currentMonth)

        binding.reservationRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adapter = createReservationAdapter
            addItemDecoration(ItemDecoration())
        }

        binding.btnConfirm.setOnClickListener {

            val bundle = bundleOf("currentDay" to datePickerAdapter.currentDay,
                "currentMonth" to createReservationAdapter.currentMonth,
            "roomTitle" to timePickerAdapter.roomTitle,"time" to timePickerAdapter.time,"imageRoom" to timePickerAdapter.imageRoom, "duration" to timePickerAdapter.duration)

            findNavController().navigate(R.id.action_createReservationFragment_to_reservationDetails,bundle)

        }

        roomObserver()
    }

    private fun roomObserver() {
        roomViewModel.roomModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.reservationRecyclerView.visibility = View.VISIBLE
                    binding.prg.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    binding.btnRefresh.visibility = View.GONE
                    createReservationAdapter.addData(it.data)
                    timePickerAdapter = TimePickerAdapter(it.data[0])


                }

                is Result.Loading -> {
                    binding.reservationRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                    binding.btnRefresh.visibility = View.GONE


                }

                is Result.Error -> {
                    binding.reservationRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    binding.btnRefresh.visibility = View.GONE
                    binding.errorText.apply {
                        visibility = View.VISIBLE
                        text = it.message
                    }
                    binding.btnRefresh.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            findNavController().navigate(R.id.action_createReservationFragment_self)
                        }
                    }


                }
            }
        }
    }
}

