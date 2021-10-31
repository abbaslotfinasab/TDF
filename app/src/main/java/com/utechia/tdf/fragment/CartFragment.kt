package com.utechia.tdf.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCartBinding
import com.utechia.tdf.viewmodel.TeaBoyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val teaBoyViewModel: TeaBoyViewModel by viewModels()
    private var refreshment:MutableList<RefreshmentModel> = mutableListOf()
    private var mId:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null) {

            mId = requireArguments().getInt("mId", 0)

        }
        teaBoyViewModel.getCard(mId)
        observer()

        binding.plusNumber.setOnClickListener {
            refreshment[0].number = refreshment[0].number?.plus(1)
            binding.numberText.text = refreshment[0].number.toString()
        }

        binding.minusNumber.setOnClickListener {

            if (refreshment[0].number!! > 0) {
                refreshment[0].number = refreshment[0].number?.minus(1)
                binding.numberText.text = refreshment[0].number.toString()
            }

        }

        binding.appCompatButton.setOnClickListener {

            teaBoyViewModel.order(refreshment)
            findNavController().navigate(R.id.action_cartFragment_to_orderResultFragment)

        }
    }

    private fun observer() {

        teaBoyViewModel.teaBoyModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.name.text = it.data[0].name
                    binding.cal.text = it.data[0].calorie.toString() + "cal"
                    binding.time.text = it.data[0].time
                    binding.numberText.text = it.data[0].number.toString()

                    Glide.with(requireActivity())
                        .load(
                            if (it.data[0].id==0)
                                R.mipmap.image1
                            else
                                R.mipmap.image2
                        )
                        .into(binding.image)

                    refreshment.addAll(it.data)

                }


                is Result.Loading -> {}

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}