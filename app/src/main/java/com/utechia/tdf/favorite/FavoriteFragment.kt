package com.utechia.tdf.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentFavoriteBinding
import com.utechia.tdf.refreshment.RefreshmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    val refreshmentViewModel: RefreshmentViewModel by viewModels()
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

        refreshmentViewModel.getRefreshment(3)
        observer()

        binding.favoriteRecycler.apply {
            adapter=favoriteAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationFavorite())
        }

    }

    private fun observer() {

        refreshmentViewModel.teaBoyModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    favoriteAdapter.addData(it.data)
                }

                is Result.Loading -> {}

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}