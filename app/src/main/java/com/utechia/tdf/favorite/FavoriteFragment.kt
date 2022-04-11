package com.utechia.tdf.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    val favoriteViewModel: FavoriteViewModel by viewModels()
    private val favoriteAdapter: FavoriteAdapter = FavoriteAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.getFavorite()

        binding.refreshLayout.setOnRefreshListener {

            favoriteViewModel.getFavorite()

        }

        binding.favoriteRecycler.apply {
            adapter=favoriteAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationFavorite())
        }

        binding.appBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
        observer()
    }

    private fun observer() {

        favoriteViewModel.favoriteModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = false

                    if (it.data.size!=0){
                        binding.favoriteRecycler.visibility = View.VISIBLE
                        favoriteAdapter.addData(it.data)
                    }else {
                        binding.favoriteRecycler.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                    }

                }

                is Result.Loading -> {
                    binding.favoriteRecycler.visibility = View.GONE
                    binding.emptyLayout.visibility = View.GONE
                    binding.prg.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = true

                }

                is Result.Error -> {
                    binding.favoriteRecycler.visibility = View.GONE
                    binding.prg.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}