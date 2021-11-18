package com.utechia.tdf.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.activity.MainActivity
import com.utechia.tdf.databinding.FragmentHomeUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

}