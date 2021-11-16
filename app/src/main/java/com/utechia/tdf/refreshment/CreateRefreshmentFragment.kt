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
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateRefreshmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentFragment : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmentBinding
    val refreshmentViewModel: RefreshmentViewModel by viewModels()
    private val searchAdapter: SearchAdapter = SearchAdapter(1)
    private val refreshmentAdapter: RefreshmentAdapter = RefreshmentAdapter()
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

        binding.appCompatButton.visibility = View.GONE


        binding.autoCompleteTextView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                searchAdapter.searchList.clear()

                if(query!="") {
                    query?.let { refreshmentViewModel.search(it) }
                    observer(0)
                }
                else
                    binding.searchRecycler.visibility = View.GONE


                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                searchAdapter.searchList.clear()


                if(newText!="") {
                    newText?.let { refreshmentViewModel.search(it) }
                    observer(0)
                }
                else
                    binding.searchRecycler.visibility = View.GONE


                return false
            }

        })


        if (arguments != null)
            category = requireArguments().getString("category", "")

        select(category)

        refreshmentViewModel.getRefreshment("food")
        observer(1)

        binding.food.setOnClickListener {

            unselect()
            select("Snacks")
            refreshmentViewModel.getRefreshment(binding.foodText.toString())
            observer(1)


        }

        binding.drink.setOnClickListener {

            unselect()
            select("Drinks")
            refreshmentViewModel.getRefreshment(binding.drinkText.toString())
            observer(1)


        }


        binding.favorite.setOnClickListener {

            unselect()
            select("Favorites")
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteFragment)

        }


        binding.order.setOnClickListener {

            unselect()
            select("Orders")
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_previousOrdersFragment)
        }


        binding.title.setOnClickListener {
            binding.title.setTextColor(Color.BLACK)
            binding.subtitle.setTextColor(Color.parseColor("#9D9D9D"))
            binding.subLine.visibility = View.GONE
            binding.titleLine.visibility = View.VISIBLE
            refreshmentViewModel.getRefreshment(binding.title.toString())
            observer(1)
        }

        binding.subtitle.setOnClickListener {
            binding.subtitle.setTextColor(Color.BLACK)
            binding.title.setTextColor(Color.parseColor("#9D9D9D"))
            binding.subLine.visibility = View.VISIBLE
            binding.titleLine.visibility = View.GONE
            refreshmentViewModel.getRefreshment(binding.subtitle.toString())
            observer(1)


        }

        binding.recyclerView.apply {
            adapter = refreshmentAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemDecorationRefreshment())
        }

        binding.appCompatButton.setOnClickListener {

            findNavController().navigate(R.id.action_createRefreshmentFragment_to_orderFragment)

        }

        binding.searchRecycler.apply {
            adapter = searchAdapter
            bringToFront()
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

    }

    private fun select(category: String) {

        when (category) {

            "Snacks" -> {

                unselect()
                binding.food.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.foodText.setTextColor(Color.WHITE)
                binding.title.visibility = View.GONE
                binding.subtitle.visibility = View.GONE

            }

            "Drinks" -> {
                unselect()
                binding.drink.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.title.visibility = View.VISIBLE
                binding.subtitle.visibility = View.VISIBLE            }

            "Orders" -> {
                unselect()
                binding.favorite.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.favoriteText.setTextColor(Color.WHITE)
                binding.title.visibility = View.GONE
                binding.subtitle.visibility = View.GONE
            }

            "Favorites" -> {
                unselect()
                binding.order.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.orderText.setTextColor(Color.WHITE)
                binding.title.visibility = View.GONE
                binding.subtitle.visibility = View.GONE            }


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

    private fun observer(kind:Int) {

        refreshmentViewModel.refreshmentModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    if (kind == 0)
                    searchAdapter.addData(it.data)
                    else
                        refreshmentAdapter.addData(it.data)
                }


                is Result.Loading -> {

                }

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}