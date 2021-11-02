package com.utechia.tdf.fragment

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
import com.utechia.tdf.adapter.RefreshmentAdapter
import com.utechia.tdf.adapter.SearchAdapter
import com.utechia.tdf.databinding.FragmentCreateRefreshmentBinding
import com.utechia.tdf.utile.ItemDecorationOrder
import com.utechia.tdf.viewmodel.SearchViewModel
import com.utechia.tdf.viewmodel.TeaBoyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentFragment : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmentBinding
    val teaBoyViewModel: TeaBoyViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchAdapter = SearchAdapter(1)
    private val refreshmentAdapter: RefreshmentAdapter = RefreshmentAdapter(this)
    private var kind = 0

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
                    query?.let { searchViewModel.getBooked(it) }
                    observe()
                }
                else
                    binding.searchRecycler.visibility = View.GONE


                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                searchAdapter.searchList.clear()


                if(newText!="") {
                    newText?.let { searchViewModel.getBooked(it) }
                    observe()
                }
                else
                    binding.searchRecycler.visibility = View.GONE


                return false
            }

        })


        if (arguments != null)
            kind = requireArguments().getInt("kind", 0)

        select(kind)

        teaBoyViewModel.getRefreshment(kind)
        observer()



        binding.food.setOnClickListener {

            unselect()
            select(1)
            teaBoyViewModel.getRefreshment(1)
            observer()


        }

        binding.drink.setOnClickListener {

            unselect()
            select(2)
            teaBoyViewModel.getRefreshment(2)
            observer()


        }


        binding.favorite.setOnClickListener {

            unselect()
            select(3)
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteFragment)

        }


        binding.order.setOnClickListener {

            unselect()
            select(4)
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_previousOrdersFragment)
        }


        binding.title.setOnClickListener {
            binding.title.setTextColor(Color.BLACK)
            binding.subtitle.setTextColor(Color.parseColor("#9D9D9D"))
            binding.subLine.visibility = View.GONE
            binding.titleLine.visibility = View.VISIBLE
        }

        binding.subtitle.setOnClickListener {
            binding.subtitle.setTextColor(Color.BLACK)
            binding.title.setTextColor(Color.parseColor("#9D9D9D"))
            binding.subLine.visibility = View.VISIBLE
            binding.titleLine.visibility = View.GONE

        }

        binding.recyclerView.apply {
            adapter = refreshmentAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemDecorationOrder())
        }

        binding.appCompatButton.setOnClickListener {

            teaBoyViewModel.order(refreshmentAdapter.orders)
            findNavController().navigate(R.id.action_createRefreshmentFragment_to_orderFragment)

        }

        binding.searchRecycler.apply {
            adapter = searchAdapter
            bringToFront()
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

    }

    private fun select(kind: Int) {

        when (kind) {

            1 -> {

                unselect()
                binding.food.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.foodText.setTextColor(Color.WHITE)
                binding.title.visibility = View.GONE
                binding.subtitle.visibility = View.GONE

            }

            2 -> {
                unselect()
                binding.drink.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.title.visibility = View.VISIBLE
                binding.subtitle.visibility = View.VISIBLE            }

            3 -> {
                unselect()
                binding.favorite.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.favoriteText.setTextColor(Color.WHITE)
                binding.title.visibility = View.GONE
                binding.subtitle.visibility = View.GONE
            }

            4 -> {
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

    private fun observer() {

        teaBoyViewModel.teaBoyModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    if(it.data.size==0)
                        binding.appCompatButton.visibility = View.GONE
                    else
                        binding.appCompatButton.visibility = View.VISIBLE

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

    fun observe(){
        searchViewModel.searchModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.searchRecycler.visibility = View.VISIBLE
                    searchAdapter.addData(it.data)
                }


                is Result.Loading -> {}


                is Result.Error -> {
                    searchAdapter.searchList.clear()
                    binding.searchRecycler.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}