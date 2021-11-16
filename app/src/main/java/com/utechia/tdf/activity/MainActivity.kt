package com.utechia.tdf.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var pref: SharedPreferences
    private var navHostFragment : NavHostFragment = NavHostFragment()
    private var navController : NavController = NavController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("tdf", Context.MODE_PRIVATE)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (!checkOutUser()){
            graph.setStartDestination(R.id.loginFragment)
        }

        else if (teaBoy()){
            graph.setStartDestination(R.id.teaBoyHomeFragment)
            setupTeaBoy()
        }
        else {
            graph.setStartDestination(R.id.userhomeFragment)
            setupUser()

        }

        navHostFragment.navController.graph = graph

        binding.customButton.apply {

            this.bringToFront()

            setOnClickListener {

                navController.navigate(R.id.qrCodeFragment)

            }
        }

        binding.menu.setOnClickListener {

            binding.drawerLayout.openDrawer(GravityCompat.END)

        }


        binding.exit.setOnClickListener {

            pref.edit().clear().apply()
            navController.navigateUp()
            navController.navigate(R.id.loginFragment)

        }

    }

    private fun checkOutUser():Boolean {

        return pref.getString("user_id",null) != null

    }


    private fun teaBoy(): Boolean {

        return pref.getBoolean("isTeaBoy",false)

    }

    fun setupUser() {

        binding.bottomNavigation.apply {
            menu.clear()
            inflateMenu(R.menu.user_bottom_nav_menu)
        }

        binding.navigationView.apply {
            menu.clear()
            inflateMenu(R.menu.user_navigation_drawer)
        }

            navController.addOnDestinationChangedListener{_, destination, _ ->
                binding.bottomNavigation.setupWithNavController(navController)

                when (destination.id){

                    R.id.userhomeFragment ->{
                        design("home")

                    }
                    R.id.refreshmentFragment ->{
                        design("refreshment")

                    }
                    R.id.reservationFragment ->{
                        design("reservation")

                    }
                    R.id.userprofileFragment ->{
                        design("profile")

                    }
                    R.id.qrCodeFragment -> {
                        design("qrcode")
                    }
                    R.id.loginFragment ->{
                        design("login")

                    }
                    R.id.authenticationFragment -> {
                        design("error")
                    }

                    else -> {
                        design("drawer")
                    }
                }

            }

            binding.bottomNavigation.setupWithNavController(navController)


            binding.navigationView.setNavigationItemSelectedListener {

                when(it.itemId) {

                    R.id.surveySystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.surveySystemFragment)

                        }, 50)

                        true
                    }
                    R.id.eventSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.eventSystemFragment)

                        }, 50)

                        true
                    }
                    R.id.ticketSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.ticketSystemFragment)

                        }, 50)


                        true
                    }

                    R.id.calendarFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.calendarFragment)

                        }, 50)


                        true
                    }

                    R.id.permissionFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.permissionFragment)

                        }, 50)

                        true
                    }

                    else -> {
                        false
                    }

                }
            }
        }

    fun setupTeaBoy() {

        binding.bottomNavigation.apply {
            menu.clear()
            inflateMenu(R.menu.teaboy_bottom_nav_menu)
        }

        binding.navigationView.apply {
            menu.clear()
            inflateMenu(R.menu.teaboy_navigation_drawer)
        }

        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNavigation.setupWithNavController(navController)

            when (destination.id){

                R.id.teaBoyHomeFragment ->{
                    design("home")

                }
                R.id.teaBoyOrdersFragment ->{
                    design("orders")

                }
                R.id.qrCodeFragment -> {
                    design("qrcode")
                }
                R.id.loginFragment ->{
                    design("login")

                }
                R.id.authenticationFragment -> {
                    design("error")
                }

                else -> {
                    design("drawer")
                }
            }

        }

        binding.bottomNavigation.setupWithNavController(navController)

        binding.navigationView.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.surveySystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.surveySystemFragment)

                    }, 50)

                    true
                }
                R.id.eventSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.eventSystemFragment)

                    }, 50)

                    true
                }
                R.id.ticketSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.ticketSystemFragment)

                    }, 50)


                    true
                }

                R.id.calendarFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.calendarFragment)

                    }, 50)


                    true
                }

                R.id.permissionFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.permissionFragment)

                    }, 50)

                    true
                }

                else -> {
                    false
                }

            }

        }

    }


    private fun design(title:String){

        when(title){

            "login" ->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.white)
                binding.toolbar.visibility = View.GONE
                binding.customButton.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "home" ->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "refreshment" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)


            }

            "qrcode"->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "reservation"->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "profile"->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

        }
    }

}