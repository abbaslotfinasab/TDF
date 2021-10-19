package com.utechia.tdf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.utechia.domain.utile.Result
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.tdf.adapter.BookedAdapter
import com.utechia.tdf.databinding.FragmentReservationBinding
import com.utechia.tdf.utile.ItemDecorationBooked
import com.utechia.tdf.viewmodel.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationFragment : Fragment() {

    private lateinit var binding: FragmentReservationBinding
    private val reservationViewModel:ReservationViewModel by viewModels()
    private val bookedAdapter:BookedAdapter = BookedAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.plus.setOnClickListener {

/*
            findNavController().navigate(R.id.action_reservationFragment_to_createReservationFragment)
*/
        }


        binding.bookedRecyclerView.apply {
            adapter =bookedAdapter
            addItemDecoration(ItemDecorationBooked())
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
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


                    }
                    else{
                        binding.createLayout.visibility = View.GONE
                        binding.bookedRecyclerView.visibility = View.VISIBLE
                        binding.prg.visibility = View.GONE
                        bookedAdapter.addData(it.data)

                    }

                }

                is Result.Loading ->{
                    binding.createLayout.visibility = View.GONE
                    binding.bookedRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error ->{
                    binding.createLayout.visibility = View.GONE
                    binding.bookedRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}