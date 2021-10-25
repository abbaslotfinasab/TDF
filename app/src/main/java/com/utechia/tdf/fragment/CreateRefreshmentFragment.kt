package com.utechia.tdf.fragment

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
import com.utechia.tdf.adapter.RefreshmentAdapter
import com.utechia.tdf.databinding.FragmentCreateRefreshmentBinding
import com.utechia.tdf.utile.ItemDecorationOrder
import com.utechia.tdf.viewmodel.TeaBoyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentFragment : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmentBinding
    val teaBoyViewModel: TeaBoyViewModel by viewModels()
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
            teaBoyViewModel.getRefreshment(3)
            observer()


        }


        binding.order.setOnClickListener {

            unselect()
            select(4)
            teaBoyViewModel.getRefreshment(4)
            observer()
        }

        binding.recyclerView.apply {
            adapter = refreshmentAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemDecorationOrder())
        }

    }

    private fun select(kind: Int) {

        when (kind) {

            1 -> {

                unselect()
                binding.food.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.foodText.setTextColor(Color.WHITE)
                binding.title.text = binding.foodText.text.toString()

            }

            2 -> {
                unselect()
                binding.drink.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.drinkText.setTextColor(Color.WHITE)
                binding.title.text = binding.drinkText.text.toString()
            }

            3 -> {
                unselect()
                binding.favorite.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.favoriteText.setTextColor(Color.WHITE)
                binding.title.text = binding.favoriteText.text.toString()

            }

            4 -> {
                unselect()
                binding.order.setBackgroundColor(Color.parseColor("#335DE0"))
                binding.orderText.setTextColor(Color.WHITE)
                binding.title.text = binding.orderText.text.toString()
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

        teaBoyViewModel.teaBoyModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
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