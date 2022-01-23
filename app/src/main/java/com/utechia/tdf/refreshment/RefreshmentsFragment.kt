package com.utechia.tdf.refreshment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentRefreshmentBinding
import com.utechia.tdf.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshmentsFragment : Fragment() {

    private lateinit var binding: FragmentRefreshmentBinding
    private lateinit var prefs: SharedPreferences
    private var order = 0
    private var name = ""
    private var job = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefreshmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences("tdf", AppCompatActivity.MODE_PRIVATE)

        if (prefs.getBoolean("Start",false)) {

            name = prefs.getString("name", "").toString()
            job = prefs.getString("job", "").toString()
            (activity as MainActivity).setupUser(name, job)

            with(prefs.edit()){

                putBoolean("Start",false)

            }.apply()

        }

        order = prefs.getInt("order",0)

        if (order!=0){
            binding.bubble.visibility = View.VISIBLE
            binding.orderNumber.visibility = View.VISIBLE
            binding.orderNumber.text = order.toString()
            binding.status.text = "Your orders are preparing"
        }else{
            binding.bubble.visibility = View.GONE
            binding.orderNumber.visibility = View.GONE
            binding.status.text = "No active orders"

        }


        binding.food.setOnClickListener {

            val bundle = bundleOf("category" to "food")
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                bundle
            )
        }

        binding.drink.setOnClickListener {

            val bundle = bundleOf("category" to "hot_drink")
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_createRefreshmentFragment,
                bundle

            )

        }

        binding.favorite.setOnClickListener {
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_favoriteFragment,
            )

        }

        binding.order.setOnClickListener {
            findNavController().navigate(
                R.id.action_refreshmentFragment_to_orderFragment,

                )
        }
    }
}