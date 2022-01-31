package com.utechia.tdf.refreshment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.utechia.domain.enum.RefreshmentEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateRefreshmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentCreateRefreshmentBinding
    val refreshmentViewModel: RefreshmentViewModel by viewModels()
    private lateinit var refreshmentViewPagerAdapter:RefreshmentViewPagerAdapter
    private var category = RefreshmentEnum.Food.refreshment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRefreshmentBinding.inflate(inflater, container, false)
        binding.food.setOnClickListener(this)
        binding.drink.setOnClickListener(this)
        binding.order.setOnClickListener(this)
        binding.favorite.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null)
            category = requireArguments().getString(RefreshmentEnum.Category.refreshment, "")

        refreshmentViewPagerAdapter = RefreshmentViewPagerAdapter(childFragmentManager, lifecycle)
        binding.pager.adapter = refreshmentViewPagerAdapter

        binding.autoCompleteTextView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != "") {
                    query?.let { refreshmentViewModel.search(it, category) }
                } else {
                    query.let { refreshmentViewModel.getRefreshment(category) }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != "") {
                    newText?.let { refreshmentViewModel.search(it, category) }
                } else {
                    newText.let { refreshmentViewModel.getRefreshment(category) }
                }

                return false
            }

        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        select(RefreshmentEnum.Hot.refreshment)
                    }

                    1 -> {
                        select(RefreshmentEnum.Cold.refreshment)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

              /*   when(tab?.position){

                     0 -> {
                         category = "hot_drink"
                         refreshmentViewModel.getRefreshment(category)
                     }
                     1 -> {
                         category = "cold_drink"
                         refreshmentViewModel.getRefreshment(category)
                     }


                 }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

              /*    when(tab?.position){

                      0 -> {
                          category = "hot_drink"
                          refreshmentViewModel.getRefreshment(category)
                      }
                      1 -> {
                          category = "cold_drink"
                          refreshmentViewModel.getRefreshment(category)
                      }


                  }*/
            }

        })

        binding.pager.isUserInputEnabled = false

        select(category)
    }

    private fun select(category: String) {
        unselect()

        when (category) {

            RefreshmentEnum.Food.refreshment -> {
                binding.pager.postDelayed({
                    binding.pager.setCurrentItem(0,true)

                },100)
                binding.food.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.foodText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE
                refreshmentViewModel.getRefreshment(category)

            }

            RefreshmentEnum.Hot.refreshment -> {
                binding.pager.postDelayed({
                    binding.pager.setCurrentItem(1,true)

                },100)
                binding.tabLayout.getTabAt(0)?.select()
                binding.drink.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_selected))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.VISIBLE
                refreshmentViewModel.getRefreshment(category)

            }

            RefreshmentEnum.Cold.refreshment -> {
                binding.pager.postDelayed({
                    binding.pager.setCurrentItem(2,true)

                },100)
                binding.tabLayout.getTabAt(1)?.select()
                binding.drink.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_selected))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.VISIBLE
                refreshmentViewModel.getRefreshment(category)

            }

            RefreshmentEnum.Order.refreshment -> {
                binding.order.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_selected))
                binding.orderText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }

            RefreshmentEnum.Favorite.refreshment -> {
                binding.favorite.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_selected))
                binding.favoriteText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }
        }

    }

    private fun unselect() {

        binding.food.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_unselected))
        binding.drink.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_unselected))
        binding.order.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_unselected))
        binding.favorite.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.refreshment_unselected))
        binding.foodText.setTextColor(Color.BLACK)
        binding.drinkText.setTextColor(Color.BLACK)
        binding.orderText.setTextColor(Color.BLACK)
        binding.favoriteText.setTextColor(Color.BLACK)

    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.food -> {

                category = RefreshmentEnum.Food.refreshment

            }

            R.id.drink -> {

                category = RefreshmentEnum.Hot.refreshment

            }

            R.id.favorite -> {

                category = RefreshmentEnum.Favorite.refreshment
                findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteFragment)

            }

            R.id.order -> {

                category = RefreshmentEnum.Order.refreshment
                findNavController().navigate(R.id.action_createRefreshmentFragment_to_orderFragment)
            }

            R.id.appBackButton -> {

                findNavController().navigate(R.id.action_createRefreshmentFragment_to_cartFragment)
            }
        }
        select(category)
    }
}