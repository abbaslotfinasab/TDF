package com.utechia.tdf.permission

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
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentPermissionChildBinding
import com.utechia.tdf.order.user.ItemDecorationOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionChildFragment(val permission: String) : Fragment() {

    private lateinit var binding:FragmentPermissionChildBinding
    private val permissionViewModel:PermissionViewModel by viewModels()
    private val permissionAdapter:PermissionAdapter = PermissionAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionViewModel.getPermission(permission)

        binding.refreshLayout.setOnRefreshListener {

            permissionViewModel.getPermission(permission)

        }

        binding.recyclerView.apply {
            adapter = permissionAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationOrder())
        }


        binding.more.setOnClickListener {
            findNavController().navigate(R.id.action_permissionFragment_to_calendarRequestFragment)
        }

        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_permissionFragment_to_calendarRequestFragment)
        }

        observer()
    }

    fun observer() {
        permissionViewModel.permissionModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.refreshLayout.isRefreshing=false
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.createLayout.visibility = View.GONE
                        binding.more.visibility = View.VISIBLE
                        permissionAdapter.addData(it.data)

                    }
                    else{
                        binding.recyclerView.visibility = View.GONE
                        binding.createLayout.visibility = View.VISIBLE
                        binding.more.visibility = View.GONE
                    }

                }

                is Result.Loading -> {
                    binding.refreshLayout.isRefreshing=true
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.createLayout.visibility = View.GONE
                    binding.more.visibility = View.GONE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.createLayout.visibility = View.VISIBLE
                    binding.more.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}