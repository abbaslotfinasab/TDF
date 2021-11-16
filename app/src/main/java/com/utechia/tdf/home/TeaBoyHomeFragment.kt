package com.utechia.tdf.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.activity.MainActivity
import com.utechia.tdf.databinding.FragmentTeaBoyHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyHomeFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}