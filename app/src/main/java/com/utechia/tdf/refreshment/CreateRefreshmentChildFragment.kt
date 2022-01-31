package com.utechia.tdf.refreshment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.tdf.databinding.FragmentCreateRefreshmenChildtBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRefreshmentChildFragment(val refreshment: String) : Fragment() {

    private lateinit var binding: FragmentCreateRefreshmenChildtBinding
    private lateinit var navHostFragment : NavHostFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRefreshmenChildtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment
        val parent = navHostFragment.childFragmentManager.primaryNavigationFragment as CreateRefreshmentFragment

        binding.recyclerView.apply {
            adapter = parent.refreshmentAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemDecorationRefreshment())
        }
    }
}