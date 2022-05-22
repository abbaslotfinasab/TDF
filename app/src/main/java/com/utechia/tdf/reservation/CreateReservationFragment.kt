package com.utechia.tdf.reservation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class CreateReservationFragment : Fragment() {

    private lateinit var binding: FragmentCreateReservationBinding
    private val roomViewModel: RoomViewModel by viewModels()
    private var title = ""
    private var cover = ""
    private var roomId = 0

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
            title = requireArguments().getString(ReservationEnum.Title.reservation, "")
            cover = requireArguments().getString(ReservationEnum.Cover.reservation, "")
            roomId = requireArguments().getInt(ReservationEnum.ID.reservation, 0)
        }

        if (cover.isEmpty()){
            binding.title.setTextColor(Color.BLACK)
            binding.btnSelect.visibility = View.VISIBLE
            binding.imageRoom.visibility = View.GONE
            binding.roomTitle.visibility = View.GONE
            binding.btnRoom.visibility = View.GONE
        }else{
            binding.title.setTextColor(Color.WHITE)
            binding.btnSelect.visibility = View.GONE
            binding.imageRoom.visibility = View.VISIBLE
            binding.roomTitle.visibility = View.VISIBLE
            binding.btnRoom.visibility = View.VISIBLE


            binding.roomTitle.text = title

            Glide.with(requireActivity())
                .load(cover)
                .transform(BlurTransformation(10,2))
                .into(binding.imageRoom)
        }

        binding.btnSelect.setOnClickListener {
            findNavController().navigate(R.id.action_createReservationFragment_to_roomListFragment)
        }
        binding.btnRoom.setOnClickListener {
            findNavController().navigate(R.id.action_createReservationFragment_to_roomListFragment)
        }

        roomObserver()
    }

    private fun roomObserver() {
        roomViewModel.roomModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {

                }

                is Result.Loading -> {

                }

                is Result.Error -> {

                }
            }
        }
    }
}

