package com.utechia.tdf.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNavigation.setupWithNavController(navController)
           when (destination.id){

                R.id.homeFragment,
                R.id.teaBoyFragment,
                R.id.reservationFragment,
                R.id.profileFragment,
                R.id.qrCodeFragment -> {

                    binding.customButton.visibility = View.VISIBLE
                    binding.bottomNavigation.visibility = View.VISIBLE

                }
                else -> {

                    binding.customButton.visibility = View.GONE
                    binding.bottomNavigation.visibility = View.GONE

                }

        }



        }

        binding.bottomNavigation.setupWithNavController(navController)

        binding.menu.setOnClickListener {

            binding.drawerLayout.openDrawer(GravityCompat.END)

        }

        binding.customButton.apply {

            this.bringToFront()

            setOnClickListener {

                navController.navigate(R.id.qrCodeFragment)

            }
        }


        binding.navigationView.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.surveySystemFragment -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.surveySystemFragment)

                    }, 300)

                    true
                }
                R.id.eventSystemFragment -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.eventSystemFragment)

                    }, 300)

                    true
                }
                R.id.ticketSystemFragment -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.ticketSystemFragment)

                    }, 300)


                    true
                }

                R.id.calendarFragment -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.calendarFragment)

                    }, 300)

                    true
                }

                R.id.permissionFragment -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.permissionFragment)

                    }, 300)

                    true
                }
                R.id.exit -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                    }, 300)


                    true
                }

                else -> {
                    false
                }

            }

        }

    }

}