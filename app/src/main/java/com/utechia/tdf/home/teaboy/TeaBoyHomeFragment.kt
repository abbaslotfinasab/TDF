package com.utechia.tdf.home.teaboy

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.main.MainActivity
import com.utechia.tdf.databinding.FragmentTeaBoyHomeBinding
import com.utechia.tdf.order.teaboy.OrderCountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyHomeFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentTeaBoyHomeBinding
    private val orderViewModel: OrderCountViewModel by viewModels()
    private lateinit var bundle: Bundle
    private lateinit var prefs: SharedPreferences
    private var name = ""
    private var floor = ""
    private var status = false

    companion object{
        const val Start = "start"
        const val Name = "name"
        const val Floor = "floor"
        const val Active = "isTeaBoyActive"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyHomeBinding.inflate(inflater, container, false)
        binding.pendingLayout.setOnClickListener(this)
        binding.deliveredLayout.setOnClickListener(this)
        binding.cancelledLayout.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)
        name = prefs.getString(Name, "").toString()
        floor = prefs.getString(Floor, "").toString()

        if (prefs.getBoolean(Start,false)) {

            (activity as MainActivity).setupTeaBoy()

            with(prefs.edit()){

                putBoolean(Start,false)

            }.apply()

        }

        binding.name.text = name
        binding.job.text = "${floor}st Floor TeaBoy"


        /* binding.token.setOnClickListener {

             val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
             val clip = ClipData.newPlainText("label",prefs.getString("fcm", ""))
             clipboard.setPrimaryClip(clip)
             Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show()
         }
 */
        orderViewModel.getOrder()


        status = prefs.getBoolean(Active,true)
        binding.switchCompat.isChecked = status

        if (status){
            binding.status.text = getString(R.string.active)
        }else
            binding.status.text = getString(R.string.deactive)


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

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.pendingLayout -> {
                bundle = bundleOf(OrderEnum.Type.order to OrderEnum.Pending.order)
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_teaBoyOrdersFragment,bundle)

            }

            R.id.deliveredLayout -> {
                bundle = bundleOf(OrderEnum.Type.order  to OrderEnum.Delivered.order)
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_teaBoyOrdersFragment,bundle)


            }

            R.id.cancelledLayout -> {
                bundle = bundleOf(OrderEnum.Type.order  to OrderEnum.Cancel.order)
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_teaBoyOrdersFragment,bundle)


            }
        }
    }
}