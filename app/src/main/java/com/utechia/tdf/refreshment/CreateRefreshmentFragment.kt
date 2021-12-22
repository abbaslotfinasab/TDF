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
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateRefreshmentBinding
import com.utechia.tdf.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentFragment : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmentBinding
    private val refreshmentViewModel: RefreshmentViewModel by viewModels()
    val favoriteViewModel: FavoriteViewModel by viewModels()
    val cartViewModel:CartViewModel by viewModels()
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
                    observer()
                }
                else{
                    query.let { refreshmentViewModel.getRefreshment(category) }
                    observer()
                }

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != "") {
                    newText?.let { refreshmentViewModel.search(it,category) }
                    observer()
                }
                else{
                    newText.let { refreshmentViewModel.getRefreshment(category) }
                    observer()
                }

                return false
            }

        })


        if (arguments != null)
            category = requireArguments().getString("category", "")

        select(category)

        refreshmentViewModel.getRefreshment(category)
        observer()

        binding.food.setOnClickListener {

            category = "food"
            unselect()
            select("food")
            refreshmentViewModel.getRefreshment(category)
            observer()


        }

        binding.drink.setOnClickListener {
            category = "hot_drink"
            unselect()
            select("hot_drink")
            refreshmentViewModel.getRefreshment("hot_drink")
            observer()


        }


        binding.favorite.setOnClickListener {

            unselect()
            select("Favorites")
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteFragment)

        }


        binding.order.setOnClickListener {

            unselect()
            select("Orders")
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_orderFragment)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        category = "hot_drink"
                        refreshmentViewModel.getRefreshment(category)
                        observer()
                    }
                    1 -> {
                        category = "cold_drink"
                        refreshmentViewModel.getRefreshment(category)
                        observer()
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

              /*   when(tab?.position){

                     0 -> {
                         category = "hot_drink"
                         refreshmentViewModel.getRefreshment(category)
                         observer()
                     }
                     1 -> {
                         category = "cold_drink"
                         refreshmentViewModel.getRefreshment(category)
                         observer()
                     }


                 }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

              /*    when(tab?.position){

                      0 -> {
                          category = "hot_drink"
                          refreshmentViewModel.getRefreshment(category)
                          observer()
                      }
                      1 -> {
                          category = "cold_drink"
                          refreshmentViewModel.getRefreshment(category)
                          observer()
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
    }

    private fun select(category: String) {

        when (category) {

            "food" -> {

                unselect()
                binding.food.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.foodText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }

            "hot_drink" -> {
                unselect()
                binding.drink.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.VISIBLE
            }

            "Orders" -> {
                unselect()
                binding.order.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.orderText.setTextColor(Color.WHITE)
                binding.tabLayout.visibility = View.GONE

            }

            "Favorites" -> {
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