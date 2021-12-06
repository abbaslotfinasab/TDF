package com.utechia.tdf.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.activity.MainActivity
import com.utechia.tdf.databinding.FragmentHomeUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeUserBinding
    private lateinit var prefs: SharedPreferences
    private var name = ""
    private var job = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)

        if (prefs.getBoolean("Start",false)) {

            name = prefs.getString("name", "").toString()
            job = prefs.getString("job", "").toString()
            (activity as MainActivity).setupUser(name, job)

            with(prefs.edit()){

                putBoolean("Start",false)

            }.apply()

        }









    }

}