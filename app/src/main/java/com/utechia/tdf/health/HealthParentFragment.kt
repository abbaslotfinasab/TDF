package com.utechia.tdf.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.OrderEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentHealthParentBinding
import com.utechia.tdf.databinding.FragmentOrderBinding
import com.utechia.tdf.order.user.UserViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthParentFragment : Fragment() {

    private lateinit var binding: FragmentHealthParentBinding
    private lateinit var healthViewPagerAdapter: HealthViewPagerAdapter
    private var type = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        healthViewPagerAdapter = HealthViewPagerAdapter(childFragmentManager, lifecycle)

        binding.pager.adapter = healthViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(OrderEnum.Type.order, "")
        }

        val tabTitles =
            listOf(getText(R.string.daily), getText(R.string.weekly), getText(R.string.monthly))
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()


    }
}