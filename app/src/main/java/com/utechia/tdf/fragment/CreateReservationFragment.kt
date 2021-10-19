package com.utechia.tdf.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
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

        binding.reservationRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adapter = createReservationAdapter
            addItemDecoration(ItemDecorationReservation())
        }

        roomObserver()
    }

    private fun roomObserver() {
        roomViewModel.roomModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {

                    binding.reservationRecyclerView.visibility = View.VISIBLE
                    binding.prg.visibility = View.GONE
                    createReservationAdapter.addData(it.data)

                }

                is Result.Loading -> {
                    binding.reservationRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.reservationRecyclerView.visibility = View.GONE
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}

