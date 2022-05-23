package com.utechia.tdf.reservation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentInvitePeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvitePeopleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInvitePeopleBinding
    private lateinit var invitePeopleViewPagerAdapter: InvitePeopleViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvitePeopleBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setOnShowListener{dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheetInternal: FrameLayout? = d.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                BottomSheetBehavior.from(bottomSheetInternal!!).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return binding.root
    }

    override fun getTheme(): Int = R.style.RoomBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invitePeopleViewPagerAdapter = InvitePeopleViewPagerAdapter(childFragmentManager,lifecycle)

        binding.pager.adapter = invitePeopleViewPagerAdapter

        val tabTitles =
            listOf(getText(R.string.search_people), getText(R.string.add_guest))
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }
}

