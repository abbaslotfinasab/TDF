package com.utechia.tdf.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.OrderEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReservationBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationFragment : Fragment() {

    private var type:String = ""
    private lateinit var binding: FragmentReservationBinding
    private lateinit var reservationViewPagerAdapter: ReservationViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationViewPagerAdapter = ReservationViewPagerAdapter(childFragmentManager, lifecycle)

        binding.pager.adapter = reservationViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(OrderEnum.Type.order, "")
        }

        val tabTitles =
            listOf(getText(R.string.my_meetings), getText(R.string.invitations))
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}