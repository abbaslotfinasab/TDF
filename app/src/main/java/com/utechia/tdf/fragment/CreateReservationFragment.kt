package com.utechia.tdf.fragment
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.adapter.CreateReservationAdapter
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import com.utechia.tdf.utile.ItemDecorationReservation
import com.utechia.tdf.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReservationFragment : Fragment() {

    private lateinit var binding: FragmentCreateReservationBinding
    private val roomViewModel: RoomViewModel by viewModels()

    private var createReservationAdapter: CreateReservationAdapter = CreateReservationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        roomViewModel.getRoom(17, 10)

        binding.reservationRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adapter = createReservationAdapter
            addItemDecoration(ItemDecorationReservation())
        }

        binding.btnConfirm.setOnClickListener {

            if (createReservationAdapter.selectedRoom.size!=0){

                if(createReservationAdapter.selectedTime.size>1) {

                    val lastSelectedPosition = createReservationAdapter.selectedRoom.elementAt(0)

                    createReservationAdapter.sortTime()

                    val bundle = bundleOf(
                        "currentDay" to createReservationAdapter.currentDay,
                        "currentMonth" to createReservationAdapter.currentMonth,
                        "roomId" to createReservationAdapter.roomModel[lastSelectedPosition].id,
                        "roomTitle" to createReservationAdapter.roomModel[lastSelectedPosition].name,
                        "capacity" to createReservationAdapter.roomModel[lastSelectedPosition].capacity,
                        "imageRoom" to createReservationAdapter.roomModel[lastSelectedPosition].image,
                        "time" to createReservationAdapter.finalTime,
                        "duration" to createReservationAdapter.duration,
                        "description" to ""
                    )


                    findNavController().navigate(
                        R.id.action_createReservationFragment_to_reservationDetails,
                        bundle
                    )
                }

                else {
                    Toast.makeText(context, "Please select two times", Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(context,"Please select one room",Toast.LENGTH_SHORT).show()

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

