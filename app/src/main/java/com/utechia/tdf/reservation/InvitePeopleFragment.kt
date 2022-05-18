package com.utechia.tdf.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.tdf.databinding.FragmentAddGuestBinding
import com.utechia.tdf.databinding.FragmentInvitePeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvitePeopleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInvitePeopleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvitePeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

