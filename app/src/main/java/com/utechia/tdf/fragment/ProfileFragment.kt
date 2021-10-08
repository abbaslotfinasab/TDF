package com.utechia.tdf.fragment

import android.os.Bundle
import com.utechia.tdf.databinding.FragmentProfileBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.tdf.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawerLayout: DrawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        val itemMenu: ImageView = requireActivity().findViewById(R.id.menu)
        val itemNotification: ImageView = requireActivity().findViewById(R.id.notification)
        val button: ImageButton = requireActivity().findViewById(R.id.customButton)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val back: ImageView = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)

        bottomNavigationView.visibility = View.VISIBLE
        itemNotification.isEnabled = true
        button.visibility = View.VISIBLE
        itemMenu.visibility = View.VISIBLE
        back.visibility = View.GONE
        name.visibility = View.GONE
        title.visibility = View.VISIBLE
        subTitle.visibility = View.VISIBLE



    }

}