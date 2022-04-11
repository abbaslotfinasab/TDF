package com.utechia.tdf.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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

                    it.data.observe(viewLifecycleOwner) { it1 ->
                        permissionAdapter.submitData(lifecycle,it1)
                    }

                    permissionAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && permissionAdapter.itemCount < 1) {
                            binding.recyclerView.visibility = View.GONE
                            binding.prg.visibility = View.GONE
                            binding.createLayout.visibility = View.VISIBLE
                            binding.refreshLayout.isRefreshing = false

                        }else if (loadState.source.refresh is LoadState.Loading ){
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = true
                            binding.recyclerView.visibility = View.GONE
                            binding.createLayout.visibility = View.GONE
                        }else{
                            binding.prg.visibility = View.GONE
                            binding.refreshLayout.isRefreshing = false
                            binding.recyclerView.visibility = View.VISIBLE
                        }
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