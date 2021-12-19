package com.utechia.tdf.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.utechia.tdf.databinding.FragmentProfileBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profilePicture.bringToFront()
        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)

        binding.name.text = prefs.getString("name","")
        binding.email.text = prefs.getString("mail","")
        binding.job.text = prefs.getString("job","")



    }

}