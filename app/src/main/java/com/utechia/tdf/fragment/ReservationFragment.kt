package com.utechia.tdf.fragment



import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.utechia.domain.utile.Result
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.tdf.R
import com.utechia.tdf.adapter.BookedAdapter
import com.utechia.tdf.databinding.FragmentReservationBinding
import com.utechia.tdf.viewmodel.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationFragment : Fragment() {

    private lateinit var binding: FragmentReservationBinding
    private val reservationViewModel:ReservationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val main: ConstraintLayout = activity?.findViewById(R.id.mainLayout)!!
        val itemMenu: ImageView = requireActivity().findViewById(R.id.menu)
        val itemNotification: ImageView = requireActivity().findViewById(R.id.notification)
        val button: ImageButton = requireActivity().findViewById(R.id.customButton)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val back: ConstraintLayout = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)

        bottomNavigationView.visibility = View.VISIBLE
        itemNotification.isEnabled = true
        button.visibility = View.VISIBLE
        itemMenu.visibility = View.VISIBLE
        back.visibility = View.GONE
        name.visibility = View.GONE
        title.visibility = View.VISIBLE
        subTitle.visibility = View.VISIBLE

        main.background = ColorDrawable(resources.getColor(R.color.gray))

        binding.plus.setOnClickListener {

            findNavController().navigate(R.id.action_reservationFragment_to_createReservationFragment)
        }

        reservationViewModel.getBooked()
        observerViewModel()

    }

    private fun observerViewModel(){

        reservationViewModel.reservationModel.observe(viewLifecycleOwner){

            when(it){
                is Result.Success -> {

                    if(it.data.size ==0){
                        binding.createLayout.visibility = View.VISIBLE
                        binding.bookedRecyclerView.visibility = View.GONE
                        binding.prg.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                        binding.btnRefresh.visibility = View.GONE

                    }
                    else{
                        binding.createLayout.visibility = View.GONE
                        binding.bookedRecyclerView.visibility = View.VISIBLE
                        binding.prg.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                        binding.btnRefresh.visibility = View.GONE

                        binding.bookedRecyclerView.apply {
                            adapter = BookedAdapter(it.data)
                            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
                        }
                    }


                }

                is Result.Loading ->{
                    binding.createLayout.visibility = View.GONE
                    binding.bookedRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                    binding.btnRefresh.visibility = View.GONE



                }

                is Result.Error ->{
                    binding.errorText.apply {
                        visibility = View.VISIBLE
                        text = it.message
                    }
                    binding.btnRefresh.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            findNavController().navigate(R.id.action_reservationFragment_self)
                        }
                    }


                }
            }

        }
    }

}