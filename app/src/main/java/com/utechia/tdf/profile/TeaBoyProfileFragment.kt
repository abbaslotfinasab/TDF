package com.utechia.tdf.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.databinding.FragmentTeaBoyProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyProfileFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

}