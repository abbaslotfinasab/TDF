package com.utechia.tdf.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.PermissionEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentPermissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : Fragment() {

    private lateinit var binding:FragmentPermissionBinding
    private lateinit var permissionViewPagerAdapter:PermissionViewPagerAdapter
    private var type = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionViewPagerAdapter = PermissionViewPagerAdapter(childFragmentManager, lifecycle)

        binding.pager.adapter=permissionViewPagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(PermissionEnum.Type.permission, "")
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->

            when (position) {

                0 -> {
                    tab.text = getText(R.string.on_going)
                }

                1 -> {
                    tab.text = getText(R.string.completed)
                }

                2 -> {
                    tab.text = getText(R.string.rejected)
                }
            }

            when (type) {

                PermissionEnum.Wait.permission -> {

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(0, true)
                    }, 300)

                }
                PermissionEnum.Expired.permission -> {

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(1, true)
                    }, 300)
                }

                PermissionEnum.Cancelled.permission -> {

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(2, true)
                    }, 300)
                }
            }
        }.attach()



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {

                    0 -> {
                        binding.pager.currentItem = tab.position
                    }
                    1 -> {
                        binding.pager.currentItem = tab.position
                    }
                    2 -> {
                        binding.pager.currentItem = tab.position
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                /*    when (tab?.position) {

                        0 -> {
                            binding.pager.currentItem = tab.position
                        }
                        1 -> {
                            binding.pager.currentItem = tab.position
                        }
                        2 -> {
                            binding.pager.currentItem = tab.position
                        }
                    }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                /* when (tab?.position) {

                     0 -> {
                         binding.pager.currentItem = tab.position
                     }
                     1 -> {
                         binding.pager.currentItem = tab.position
                     }
                     2 -> {
                         binding.pager.currentItem = tab.position
                     }
                 }*/
            }
        })
    }
}