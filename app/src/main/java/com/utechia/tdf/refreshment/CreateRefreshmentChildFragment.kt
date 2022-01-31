package com.utechia.tdf.refreshment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentCreateRefreshmenChildtBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentChildFragment(val refreshment: String) : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmenChildtBinding
    val refreshmentViewModel: RefreshmentViewModel by viewModels()
    private val refreshmentAdapter: RefreshmentAdapter = RefreshmentAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRefreshmenChildtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.prg.bringToFront()
        
        refreshmentViewModel.getRefreshment(refreshment)

        binding.recyclerView.apply {
            adapter = refreshmentAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemDecorationRefreshment())
        }

        observer()
    }

     fun observer() {

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