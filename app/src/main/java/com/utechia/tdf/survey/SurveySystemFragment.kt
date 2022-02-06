package com.utechia.tdf.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utechia.domain.enum.SurveyEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentSurveySystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveySystemFragment : Fragment() {

    private lateinit var binding:FragmentSurveySystemBinding
    private lateinit var surveyViewpagerAdapter: SurveyViewpagerAdapter
    private var type = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSurveySystemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surveyViewpagerAdapter = SurveyViewpagerAdapter(childFragmentManager, lifecycle)

        binding.pager.adapter=surveyViewpagerAdapter

        if (arguments != null) {
            type = requireArguments().getString(SurveyEnum.Type.survey, "")
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->

            when (position) {

                0 -> {
                    tab.text = getText(R.string.available_surveys)
                }

                1 -> {
                    tab.text = getText(R.string.evaluated_surveys)
                }

            }

            when (type) {

                    SurveyEnum.Evaluate.survey -> {

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(0, true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(1))

                    }, 200)

                }

                SurveyEnum.Expired.survey -> {

                    binding.pager.postDelayed({
                        binding.pager.setCurrentItem(1, true)
                        binding.tabLayout.selectTab(tab.parent?.getTabAt(0))

                    }, 200)
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
                 }*/
            }
        })
    }
}