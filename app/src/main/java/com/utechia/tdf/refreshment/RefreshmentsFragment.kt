package com.utechia.tdf.refreshment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTeaBoyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshmentsFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchAdapter = SearchAdapter(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.autoCompleteTextView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                searchAdapter.searchList.clear()

                if(query!="") {
                    query?.let { searchViewModel.getBooked(it) }
                    observer()
                }
                else
                    binding.searchRecycler.visibility = View.GONE


                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                searchAdapter.searchList.clear()


                if(newText!="") {
                    newText?.let { searchViewModel.getBooked(it) }
                    observer()
                }
                else
                    binding.searchRecycler.visibility = View.GONE


                return false
            }

        })


        binding.searchRecycler.apply {
            adapter = searchAdapter
            bringToFront()
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

        binding.food.setOnClickListener {

            val bundle = bundleOf("kind" to 1)
            findNavController().navigate(
                R.id.action_teaBoyFragment_to_createRefreshmentFragment,
                bundle
            )
        }

        binding.drink.setOnClickListener {

            val bundle = bundleOf("kind" to 2)
            findNavController().navigate(
                R.id.action_teaBoyFragment_to_createRefreshmentFragment,
                bundle
            )

        }

        binding.favorite.setOnClickListener {
            findNavController().navigate(
                R.id.action_teaBoyFragment_to_favoriteFragment,
            )

        }

        binding.order.setOnClickListener {
            findNavController().navigate(
                R.id.action_teaBoyFragment_to_previousOrdersFragment,

            )

        }
    }

    fun observer(){
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