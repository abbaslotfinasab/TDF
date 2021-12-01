package com.utechia.tdf.activity

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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.installations.InstallationTokenResult
import com.utechia.tdf.R
import com.utechia.tdf.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var navHostFragment : NavHostFragment = NavHostFragment()
    private var navController : NavController = NavController(this)
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("tdf", MODE_PRIVATE)


        FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener(object :OnCompleteListener<InstallationTokenResult>{
            override fun onComplete(task: Task<InstallationTokenResult>) {

                if (!task.isSuccessful)
                    return
                else {
                    with(prefs.edit()) {
                        putString("fcm", task.result.token)
                    }.apply()
                }
            }

        })


        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        when {

            !checkOutUser() -> {
                graph.setStartDestination(R.id.loginFragment)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }
            teaBoy() -> {
                graph.setStartDestination(R.id.teaBoyHomeFragment)

                val name = prefs.getString("name","").toString()
                val floor = prefs.getString("floor","").toString()
                setupTeaBoy(name,floor)
            }
            !teaBoy() -> {
                graph.setStartDestination(R.id.refreshmentFragment)
                val name = prefs.getString("name","").toString()
                val job = prefs.getString("job","").toString()
                setupUser(name,job)

            }
            else ->
                graph.setStartDestination(R.id.loginFragment)
        }
        navHostFragment.navController.graph = graph

        binding.customButton.apply {

            this.bringToFront()

            setOnClickListener {

                navController.navigate(R.id.qrCodeFragment)

            }
        }

        binding.backArrow.setOnClickListener{

            navController.popBackStack()

        }

        binding.menu.setOnClickListener {

            binding.drawerLayout.openDrawer(GravityCompat.END)

        }

        binding.exit.setOnClickListener {

            prefs.edit().clear().apply()
            navController.navigateUp()
            navController.navigate(R.id.loginFragment)

        }

    }

    private fun checkOutUser(): Boolean {

        return prefs.getString("USER_ID",null) !=null

    }

    private fun teaBoy(): Boolean {

        return prefs.getBoolean("isTeaBoy",false)

    }

    fun setupUser(name: String, job:String) {

        binding.title.text = name
        binding.subTitle.text = job

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
                        navController.clearBackStack(R.id.loginFragment)
                        design("home")

                    }
                    R.id.refreshmentFragment ->{
                        design("refreshment")

                    }

                    R.id.createRefreshmentFragment ->{
                        design("menu")

                    }

                    R.id.orderFragment ->{
                        navController.clearBackStack(R.id.cartFragment)
                        design("orders")

                    }
                    R.id.reservationFragment ->{
                        design("reservation")

                    }

                    R.id.favoriteFragment ->{
                        design("favorites")

                    }

                    R.id.cartFragment ->{
                        design("cart")

                    }

                    R.id.userprofileFragment ->{
                        design("profile")

                    }
                    R.id.qrCodeFragment -> {
                        design("qrcode")
                    }

                    R.id.createReservationFragment -> {
                        design("createReservationFragment")
                    }

                    R.id.loginFragment ->{
                        design("login")

                    }
                    R.id.authenticationFragment -> {
                        design("error")
                    }

                    R.id.permissionFragment -> {
                        design("permission")

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

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }
                    R.id.eventSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.eventSystemFragment)

                        }, 50)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }
                    R.id.ticketSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.ticketSystemFragment)

                        }, 50)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    R.id.calendarFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.calendarFragment)

                        }, 100)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    R.id.permissionFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigateUp()
                            navController.navigate(R.id.permissionFragment)

                        }, 50)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)


                        true
                    }

                    else -> {
                        false
                    }

                }
            }
        }

    fun setupTeaBoy(name: String, floor:String) {

        binding.title.text = name
        binding.subTitle .text = floor+"th Floor TeaBoy"

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
                    navController.clearBackStack(R.id.loginFragment)
                    design("home")

                }
                R.id.teaBoyOrdersFragment ->{
                    design("tOrders")

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

                R.id.permissionFragment -> {
                    design("permission")

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

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                R.id.eventSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.eventSystemFragment)

                    }, 50)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                R.id.ticketSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.ticketSystemFragment)

                    }, 50)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }

                R.id.calendarFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.calendarFragment)

                    }, 50)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }

                R.id.permissionFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.permissionFragment)

                    }, 50)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

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
                binding.customToolbar.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "home" ->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "refreshment" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)


            }

            "menu" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Menu"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


            }

            "orders" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Orders"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "tOrders" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.INVISIBLE
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "favorites" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Favorites"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "cart" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Cart"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "qrcode"->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "reservation"->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "createReservationFragment"->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Reservation"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

            "profile"->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            "permission" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Leave Requests"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

        }
    }
}