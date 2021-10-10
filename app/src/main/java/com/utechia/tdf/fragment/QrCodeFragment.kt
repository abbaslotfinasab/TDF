package com.utechia.tdf.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentQrCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrCodeFragment : Fragment() {

    private lateinit var binding: FragmentQrCodeBinding
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawerLayout: DrawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        val itemMenu: ImageView = requireActivity().findViewById(R.id.menu)
        val button: ImageButton = requireActivity().findViewById(R.id.customButton)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val back: ConstraintLayout = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)

        bottomNavigationView.visibility = View.GONE
        button.visibility = View.GONE
        itemMenu.visibility = View.GONE
        back.visibility = View.VISIBLE
        name.visibility = View.VISIBLE
        name.text = "Qr Code"
        title.visibility = View.GONE
        subTitle.visibility = View.GONE

         back.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()
        }

        binding.button.setOnClickListener {

            when (count%2) {

                0-> {
                    binding.load.visibility = android.view.View.VISIBLE
                    binding.button.text = "Cancel"
                    binding.button.apply {
                        background = resources.getDrawable(R.drawable.cancel_button)
                        setTextColor(resources.getColor(R.color.black))
                    }

                }
                1->{
                    binding.button.text = "Scan"
                    binding.load.visibility = View.GONE
                    binding.button.apply {
                        background = resources.getDrawable(R.drawable.scan_button)
                        setTextColor(resources.getColor(R.color.white))
                    }
                }

            }

            count += 1

        }





    }

}