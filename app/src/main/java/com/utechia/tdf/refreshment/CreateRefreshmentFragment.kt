package com.utechia.tdf.refreshment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.utechia.domain.enum.RefreshmentEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateRefreshmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentFragment : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmentBinding
    val refreshmentViewModel: RefreshmentViewModel by viewModels()
    private val refreshmentAdapter: RefreshmentAdapter = RefreshmentAdapter(this)
    private var category = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRefreshmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.autoCompleteTextView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != "") {
                    query?.let { refreshmentViewModel.search(it,category) }
                }
                else{
                    query.let { refreshmentViewModel.getRefreshment(category) }
                }

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != "") {
                    newText?.let { refreshmentViewModel.search(it,category) }
                }
                else{
                    newText.let { refreshmentViewModel.getRefreshment(category) }
                }

                return false
            }

        })

        if (arguments != null)
            category = requireArguments().getString(RefreshmentEnum.Category.refreshment, "")

        select(category)

        refreshmentViewModel.getRefreshment(category)

        binding.food.setOnClickListener {

            category = RefreshmentEnum.Food.refreshment
            unselect()
            select(category)
            refreshmentViewModel.getRefreshment(category)


        }

        binding.drink.setOnClickListener {
            category = RefreshmentEnum.Hot.refreshment
            unselect()
            select(category)
            refreshmentViewModel.getRefreshment(category)

        }


        binding.favorite.setOnClickListener {

            unselect()
            select(RefreshmentEnum.Favorite.refreshment)
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteFragment)

        }


        binding.order.setOnClickListener {

            unselect()
            select(RefreshmentEnum.Order.refreshment)
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_orderFragment)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        category = RefreshmentEnum.Hot.refreshment
                        refreshmentViewModel.getRefreshment(category)
                    }
                    1 -> {
                        category = RefreshmentEnum.Cold.refreshment
                        refreshmentViewModel.getRefreshment(category)
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

        binding.recyclerView.apply {
            adapter = refreshmentAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemDecorationRefreshment())
        }

        binding.appCompatButton.setOnClickListener {
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_cartFragment)

        }

        observer()
    }

    private fun select(category: String) {

        when (category) {

            RefreshmentEnum.Food.refreshment -> {

                unselect()
                binding.food.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.foodText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }

            RefreshmentEnum.Hot.refreshment -> {
                unselect()
                binding.drink.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.VISIBLE
            }

            RefreshmentEnum.Order.refreshment -> {
                unselect()
                binding.order.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.orderText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }

            RefreshmentEnum.Favorite.refreshment -> {
                unselect()
                binding.favorite.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.favoriteText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }
        }
    }

    private fun unselect() {

        binding.food.setBackgroundColor(Color.parseColor("#FCFCFD"))
        binding.drink.setBackgroundColor(Color.parseColor("#FCFCFD"))
        binding.order.setBackgroundColor(Color.parseColor("#FCFCFD"))
        binding.favorite.setBackgroundColor(Color.parseColor("#FCFCFD"))
        binding.foodText.setTextColor(Color.BLACK)
        binding.drinkText.setTextColor(Color.BLACK)
        binding.orderText.setTextColor(Color.BLACK)
        binding.favoriteText.setTextColor(Color.BLACK)

    }

    private fun observer() {

        refreshmentViewModel.refreshmentModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    refreshmentAdapter.addData(it.data)
                }
                is Result.Loading -> {binding.prg.visibility = View.VISIBLE}

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}