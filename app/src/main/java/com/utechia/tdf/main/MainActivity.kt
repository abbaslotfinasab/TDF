package com.utechia.tdf.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()
    private var navHostFragment : NavHostFragment = NavHostFragment()
    private var navController : NavController = NavController(this)
    private lateinit var prefs: SharedPreferences
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("tdf", MODE_PRIVATE)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                if (!task.isSuccessful)
                    return@OnCompleteListener
                else {
                    with(prefs.edit()) {
                        putString("fcm", task.result)
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
                mainViewModel.getCount()
                graph.setStartDestination(R.id.teaBoyHomeFragment)
                val name = prefs.getString("name","").toString()
                val floor = prefs.getString("floor","").toString()
                setupTeaBoy(name,floor)
                observer()
            }
            !teaBoy() -> {
                mainViewModel.getCount()
                graph.setStartDestination(R.id.userhomeFragment)
                val name = prefs.getString("name","").toString()
                val job = prefs.getString("job","").toString()
                setupUser(name,job)
                observer()
            }
            else ->
                graph.setStartDestination(R.id.loginFragment)
        }
        navHostFragment.navController.graph = graph

        binding.customButton.apply {

            this.bringToFront()

            setOnClickListener {

                binding.bottomNavigation.apply {
                    menu.setGroupCheckable(0,true,false)
                    menu.forEach {
                        it.isChecked = false
                    }
                    menu.setGroupCheckable(0,true,true)
                }

                navController.navigate(R.id.qrCodeFragment)

            }
        }

        binding.backArrow.setOnClickListener{

            navController.navigateUp()

        }

        binding.menu.setOnClickListener {

            binding.drawerLayout.openDrawer(GravityCompat.END)

        }

        binding.notification.setOnClickListener{
            navController.navigate(R.id.notificationFragment)
        }

        binding.exit.setOnClickListener {
            prefs.edit().clear().apply()

            Handler(Looper.getMainLooper()).postDelayed({
                logoutFromFCM()
                navController.popBackStack(R.id.nav_graph,true)
                navController.navigate(R.id.loginFragment)

            }, 300)

            binding.drawerLayout.closeDrawer(GravityCompat.END)

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.drawerLayout.closeDrawer(GravityCompat.END)

    }

    private fun logoutFromFCM() {

        lifecycleScope.launch(Dispatchers.IO) {
            FirebaseInstallations.getInstance().delete()
            FirebaseMessaging.getInstance().deleteToken()

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                with(prefs.edit()) {
                    putString("fcm", task.result)
                }.apply()
            })
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
                        mainViewModel.getCount()
                        navController.clearBackStack(R.id.loginFragment)
                        design("home")
                        observer()

                    }
                    R.id.refreshmentFragment ->{
                        mainViewModel.getCount()
                        design("refreshment")
                        observer()

                    }

                    R.id.createRefreshmentFragment ->{
                        design("menu")

                    }

                    R.id.orderFragment ->{
                        navController.clearBackStack(R.id.cartFragment)
                        design("orders")

                    }
                    R.id.reservationFragment ->{
                        mainViewModel.getCount()
                        design("reservation")
                        observer()

                    }

                    R.id.favoriteFragment ->{
                        design("favorites")

                    }

                    R.id.cartFragment ->{
                        design("cart")

                    }

                    R.id.userprofileFragment ->{
                        mainViewModel.getCount()
                        design("profile")
                        observer()

                    }
                    R.id.qrCodeFragment -> {
                        mainViewModel.getCount()
                        design("qrcode")
                        observer()
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

                    R.id.surveySystemFragment -> {
                        design("survey")

                    }

                    R.id.calendarFragment -> {
                        design("calendar")

                    }

                    R.id.ticketSystemFragment -> {
                        design("ticket")

                    }

                    R.id.createTicketFragment -> {
                        design("createTicket")

                    }

                    R.id.eventSystemFragment -> {
                        design("event")

                    }

                    R.id.ticketDetailsFragment -> {
                        design("ticketDetails")

                    }

                    R.id.blankFragment -> {
                        design("blank")

                    }

                    R.id.notificationFragment -> {
                        mainViewModel.getCount()
                        design("notification")
                        observer()

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

                            navController.navigate(R.id.surveySystemFragment)

                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }
                    R.id.eventSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(R.id.eventSystemFragment)

                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }
                    R.id.ticketSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(R.id.ticketSystemFragment)

                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    R.id.calendarFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(R.id.calendarFragment)

                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    R.id.permissionFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(R.id.permissionFragment)

                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    else -> {
                        false
                    }

                }
            }
        }

    private fun observer() {
        mainViewModel.mainModel.observe(this){
            when (it) {
                is Result.Success -> {
                    if (it.data.count==0){
                        binding.bubble.visibility = View.GONE
                        binding.notificationNumber.visibility = View.GONE

                    }else{
                        binding.bubble.visibility = View.VISIBLE
                        binding.notificationNumber.visibility = View.VISIBLE
                        binding.notificationNumber.text = it.data.count.toString()


                    }
                }

                is Result.Loading -> {}

                is Result.Error -> {
                    binding.bubble.visibility = View.GONE
                    binding.notificationNumber.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
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
                    mainViewModel.getCount()
                    navController.clearBackStack(R.id.loginFragment)
                    design("home")

                }
                R.id.teaBoyOrdersFragment ->{
                    mainViewModel.getCount()
                    design("tOrders")
                    observer()
                }
                R.id.qrCodeFragment -> {
                    mainViewModel.getCount()
                    design("qrcode")
                    observer()
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

                R.id.surveySystemFragment -> {
                    design("survey")

                }

                R.id.calendarFragment -> {
                    design("calendar")

                }


                R.id.ticketSystemFragment -> {
                    design("ticket")

                }

                R.id.createTicketFragment -> {
                    design("createTicket")

                }

                R.id.eventSystemFragment -> {
                    design("event")

                }

                R.id.ticketDetailsFragment -> {
                    design("ticketDetails")

                }

                R.id.blankFragment -> {
                    design("blank")

                }

                R.id.notificationFragment -> {
                    mainViewModel.getCount()
                    design("notification")
                    observer()
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

                        navController.navigate(R.id.surveySystemFragment)

                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                R.id.eventSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.eventSystemFragment)

                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                R.id.ticketSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(R.id.ticketSystemFragment)

                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }

                R.id.calendarFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(R.id.calendarFragment)

                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }

                R.id.permissionFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(R.id.permissionFragment)

                    }, 300)

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

            "survey" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Survey System"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "calendar" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Calendar"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "ticket" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Ticket"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "event" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Event System"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "createTicket"->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Add Ticket"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "ticketDetails" ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Ticket Details"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "blank"->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.black)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = ""
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            "notification"->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = "Notification"
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

        }
    }
}