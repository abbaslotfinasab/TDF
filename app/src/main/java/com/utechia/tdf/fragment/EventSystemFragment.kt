package com.utechia.tdf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentEventSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventSystemFragment : Fragment() {

    private lateinit var binding: FragmentEventSystemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val custom: ImageView = requireActivity().findViewById(R.id.customButton)
        val menu: ImageView = requireActivity().findViewById(R.id.menu)
        val back: ImageView = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)
        navBar.visibility = View.GONE
        custom.visibility = View.GONE
        menu.visibility = View.GONE
        back.visibility = View.VISIBLE
        name.visibility = View.VISIBLE
        name.text = "Event System"
        title.visibility = View.GONE
        subTitle.visibility = View.GONE


        back.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()
        }






    }

}