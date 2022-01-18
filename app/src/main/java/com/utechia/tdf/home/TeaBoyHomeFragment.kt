package com.utechia.tdf.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.main.MainActivity
import com.utechia.tdf.databinding.FragmentTeaBoyHomeBinding
import com.utechia.tdf.order.OrderCountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyHomeFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyHomeBinding
    private val orderViewModel: OrderCountViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
    private var name = ""
    private var floor = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)

        if (prefs.getBoolean("Start",false)) {

            name = prefs.getString("name", "").toString()
            floor = prefs.getString("floor", "").toString()
            (activity as MainActivity).setupTeaBoy(name,floor)

            with(prefs.edit()){

                putBoolean("Start",false)

            }.apply()

        }

       /* binding.token.setOnClickListener {

            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label",prefs.getString("fcm", ""))
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show()
        }
*/
        orderViewModel.getOrder()

        binding.switchCompat.isChecked = prefs.getBoolean("isTeaBoyActive",true)

        binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked){
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_activationFragment)
            }
            else
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_deactivationFragment)

        }
        observer()
    }

    private fun observer() {
        orderViewModel.orderModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.active.text = it.data[0].delivered
                    binding.pending.text = it.data[0].pending
                    binding.cancelled.text = it.data[0].cancelled
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}