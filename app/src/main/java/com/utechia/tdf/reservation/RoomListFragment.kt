package com.utechia.tdf.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.tdf.databinding.FragmentAddGuestBinding
import com.utechia.tdf.databinding.FragmentInvitePeopleBinding
import com.utechia.tdf.databinding.FragmentRoomListBinding
import com.utechia.tdf.databinding.FragmentSearchPeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomListFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRoomListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

