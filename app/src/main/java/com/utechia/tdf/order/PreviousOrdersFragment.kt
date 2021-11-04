package com.utechia.tdf.order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentPreviousOrdersBinding
import com.utechia.tdf.refreshment.RefreshmentViewModel
import com.utechia.tdf.favorite.ItemDecorationFavorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviousOrdersFragment : Fragment() {

    private lateinit var binding: FragmentPreviousOrdersBinding
    val refreshmentViewModel: RefreshmentViewModel by viewModels()
    private val previousOrderAdapter: PreviousOrderAdapter = PreviousOrderAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviousOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshmentViewModel.getRefreshment(4)
        observer()

        binding.orderRecycler.apply {
            adapter=previousOrderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationFavorite())
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


    }

    private fun observer() {

        refreshmentViewModel.teaBoyModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {

                    previousOrderAdapter.addData(it.data)
                }

                is Result.Loading -> {}

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}