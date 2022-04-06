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
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.utechia.data.entity.Transaction
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.main.MainActivity
import com.utechia.tdf.databinding.FragmentTeaBoyHomeBinding
import com.utechia.tdf.main.MainViewModel
import com.utechia.tdf.order.teaboy.OrderCountViewModel
import com.utechia.tdf.profile.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class TeaBoyHomeFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentTeaBoyHomeBinding
    private val orderViewModel: OrderCountViewModel by viewModels()
    private val profileViewModel: UserProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels({ requireActivity()})
    private lateinit var database: DatabaseReference
    private lateinit var bundle: Bundle
    private lateinit var prefs: SharedPreferences
    private var decimalFormat:DecimalFormat = DecimalFormat("#.#")
    private var name = ""
    private var floor = ""
    private var rate = 0f
    private var status = false
    private var employeeId = ""
    private var avg = ""


    companion object{
        const val Start = "start"
        const val Name = "name"
        const val Floor = "floor"
        const val Active = "isTeaBoyActive"
        const val ID = "employeeId"

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

        database = Firebase.database("https://tdf-oms-default-rtdb.asia-southeast1.firebasedatabase.app/").reference


        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)
        name = prefs.getString(Name, "").toString()
        floor = prefs.getString(Floor, "").toString()
        employeeId = prefs.getInt(ID, 0).toString()



        if (prefs.getBoolean(Start,false)) {

            (activity as MainActivity).setupTeaBoy()

            with(prefs.edit()){

                putBoolean(Start,false)

            }.apply()

        }

        profileViewModel.getProfile()


        orderViewModel.getOrder()

        status = prefs.getBoolean(Active,true)
        binding.switchCompat.isChecked = status

        if (status){
            binding.status.text = getString(R.string.active)
        }else
            binding.status.text = getString(R.string.deactive)


        val tranceActionListener = database.child("Transaction").child(employeeId)

        tranceActionListener.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.prg.visibility = View.GONE

                val enter = snapshot.child("first_checkin").getValue<Transaction>()
                val exit = snapshot.child("last_checkout").getValue<Transaction>()

                if (enter?.punch_time.isNullOrEmpty()){
                    binding.transactionLayout.visibility = View.GONE
                }
                else{
                    binding.transactionLayout.visibility = View.VISIBLE
                    binding.entranceDate.text = enter?.punch_time
                    binding.entranceFloor.text = enter?.area_alias

                    if (exit?.punch_time.isNullOrEmpty()){
                        binding.exitDate.visibility = View.GONE
                        binding.exitFloor.visibility = View.GONE
                    }
                    else{
                        binding.exitDate.text = exit?.punch_time
                        binding.exitFloor.text = exit?.area_alias
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }
        })



        binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked){
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_activationFragment)
            }
            else
                findNavController().navigate(R.id.action_teaBoyHomeFragment_to_deactivationFragment)

        }

        binding.pedometerLayout.setOnClickListener {
            findNavController().navigate(R.id.action_teaBoyHomeFragment_to_healthParentFragment)
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
                    rate = prefs.getFloat(MainEnum.Avg.main,0f)
                    binding.rating.rating = rate
                    avg = decimalFormat.format(rate)
                    binding.ratingNum.text = avg
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

       mainViewModel.stepCounter.observe(viewLifecycleOwner){

            binding.stepsCount.text = it.toString()
            binding.calory.text = (it*0.05).toInt().toString()

        }

        profileViewModel.profile.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.name.text = it.data.name
                    binding.job.text = "${it.data.floor}st Floor ${it.data.jobTitle}"

                    Glide.with(requireActivity())
                        .load(it.data.profilePictureModel?.url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_profile_icon)
                        .error(R.drawable.ic_profile_icon)
                        .into(binding.profilePicture)
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