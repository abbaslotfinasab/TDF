package com.utechia.tdf.main

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.hardware.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.model.reservation.ReservationModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.ActivityMainBinding
import com.utechia.tdf.reservation.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding:ActivityMainBinding
    val mainViewModel:MainViewModel by viewModels()
    private val reservationViewModel: ReservationViewModel by viewModels()
    private var navHostFragment : NavHostFragment = NavHostFragment()
    private var navController : NavController = NavController(this)
    private lateinit var prefs: SharedPreferences
    private lateinit var analytics: FirebaseAnalytics
    private var  sensorManager : SensorManager? = null
    private var  sensor : Sensor? = null
    private var previousSteps = 0
    private var totalSteps = 0
    private var currentSteps = 0
    private var startTimeZone = ""
    private var endTimeZone = ""


    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)

    }

    companion object{
        const val TOTAL_STEPS = "total_Steps"
        const val PREVIOUS_STEPS = "previous_Steps"

        private var ins:MainActivity? = null
        fun getInstance():MainActivity?{
            return ins
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = getSharedPreferences(MainEnum.Tdf.main, MODE_PRIVATE)
        setContentView(binding.root)
        ins = this

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                if (!task.isSuccessful)
                    return@OnCompleteListener
                else {
                    with(prefs.edit()) {
                        putString(MainEnum.Fcm.main, task.result)
                    }.commit()
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
                setupTeaBoy()
            }
            !teaBoy() -> {
                graph.setStartDestination(R.id.userhomeFragment)
                setupUser()
            }
            else ->
                graph.setStartDestination(R.id.loginFragment)
        }
        navHostFragment.navController.graph = graph

        binding.customButton.bringToFront()

        binding.customButton.setOnClickListener {

            navController.popBackStack()
            navController.navigate(R.id.qrCodeFragment)

        }

        binding.backArrow.setOnClickListener{
            navController.navigateUp()
            reservationViewModel.deleteAll()
        }

        binding.menu.setOnClickListener {

            binding.drawerLayout.openDrawer(GravityCompat.END)

        }

        binding.notification.setOnClickListener{

            navController.navigate(R.id.notificationFragment,null,NavOptions.Builder()
                .setEnterAnim(android.R.anim.fade_in)
                .setExitAnim(android.R.anim.fade_out)
                .setPopEnterAnim(android.R.anim.fade_out)
                .setPopEnterAnim(android.R.anim.fade_in)
                .build())

        }

        binding.exit.setOnClickListener {
            logoutFromFCM()
            prefs.edit().clear().apply()
            Handler(Looper.getMainLooper()).postDelayed({
                navController.popBackStack(R.id.nav_graph,true)
                navController.navigate(R.id.loginFragment)

            }, 300)

            binding.drawerLayout.closeDrawer(GravityCompat.END)


        }

        observer()

        createPeriodTimeRequest()
    }

    override fun onResume() {
        super.onResume()

        this.registerReceiver(MyBroadCastReceiver(),IntentFilter("counter"))

        mainViewModel.sendToken()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        previousSteps = prefs.getInt(PREVIOUS_STEPS,0)
        totalSteps = prefs.getInt(TOTAL_STEPS,0)

        if(totalSteps<previousSteps || previousSteps==0){
            with(prefs.edit()) {
                putInt(PREVIOUS_STEPS, totalSteps)
            }.apply()

            previousSteps = prefs.getInt(PREVIOUS_STEPS,0)
        }

        currentSteps = totalSteps - previousSteps

        mainViewModel.stepCount(currentSteps)

        startTimeZone = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        endTimeZone = LocalDateTime.now().atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        if(currentSteps>0)
        mainViewModel.sendSteps(currentSteps,(currentSteps*0.05).toInt(),startTimeZone,endTimeZone)

    }

    override fun onPause() {
        try {

            this.unregisterReceiver(MyBroadCastReceiver())

        } catch (e: Exception) {
            // already unregistered
        }
        super.onPause()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        binding.drawerLayout.closeDrawer(GravityCompat.END)
        reservationViewModel.deleteAll()

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
                    putString(MainEnum.Fcm.main, task.result)
                }.apply()
            })
        }
    }

    private fun checkOutUser(): Boolean {

        return prefs.getString(MainEnum.HomeId.main,"")?:"" != ""
    }

    private fun teaBoy(): Boolean {

        return prefs.getBoolean(MainEnum.TeaBoy.main,false)

    }

    fun setupUser() {

        binding.bottomNavigation.apply {
            menu.clear()
            inflateMenu(R.menu.user_bottom_nav_menu)
            this.itemTextAppearanceActive = R.style.userMenuItems
            this.itemTextAppearanceInactive = R.style.userMenuItems

        }

        binding.navigationView.apply {
            menu.clear()
            inflateMenu(R.menu.user_navigation_drawer)
        }

            navController.addOnDestinationChangedListener{_, destination, _ ->
                binding.bottomNavigation.setupWithNavController(navController)

                if(destination.id != R.id.loginFragment) {
                    mainViewModel.getCount()
                }

                when (destination.id){

                    R.id.userhomeFragment ->{
                        design(MainEnum.Home.main)

                    }
                    R.id.refreshmentFragment ->{
                        design(MainEnum.Refreshment.main)
                    }

                    R.id.createRefreshmentFragment ->{
                        design(MainEnum.Menu.main)

                    }

                    R.id.orderFragment ->{
                        design(MainEnum.UserOrder.main)

                    }

                    R.id.reservationFragment ->{
                        design(MainEnum.Reservation.main)

                    }

                    R.id.favoriteFragment ->{
                        design(MainEnum.Favorite.main)

                    }

                    R.id.cartFragment ->{
                        design(MainEnum.Cart.main)

                    }

                    R.id.locationOrderFragment ->{
                        design(MainEnum.Location.main)

                    }

                    R.id.userprofileFragment ->{
                        design(MainEnum.Profile.main)

                    }
                    R.id.qrCodeFragment -> {
                        design(MainEnum.Code.main)
                    }

                    R.id.createReservationFragment -> {
                        design(MainEnum.CreateReservation.main)
                    }

                    R.id.loginFragment ->{
                        design(MainEnum.Login.main)

                    }
                    R.id.authenticationFragment -> {
                        design(MainEnum.Error.main)
                    }

                    R.id.permissionFragment -> {
                        design(MainEnum.Permission.main)

                    }

                    R.id.surveySystemFragment -> {
                        design(MainEnum.Survey.main)

                    }

                    R.id.calendarFragment -> {
                        design(MainEnum.Calendar.main)

                    }

                    R.id.ticketSystemFragment -> {
                        design(MainEnum.Ticket.main)

                    }

                    R.id.createTicketFragment -> {
                        design(MainEnum.CreateTicket.main)

                    }

                    R.id.eventSystemFragment -> {
                        design(MainEnum.Event.main)

                    }

                    R.id.eventDetailsFragment -> {
                        design(MainEnum.EventDetails.main)

                    }

                    R.id.ticketDetailsFragment -> {
                        design(MainEnum.TicketDetails.main)

                    }

                    R.id.blankFragment -> {
                        design(MainEnum.Blank.main)

                    }

                    R.id.notificationFragment -> {
                        design(MainEnum.Notification.main)

                    }

                    R.id.healthParentFragment -> {
                        design(MainEnum.Health.main)

                    }
                }
            }

            binding.bottomNavigation.setupWithNavController(navController)

            binding.navigationView.setNavigationItemSelectedListener {

                when(it.itemId) {

                    R.id.surveySystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(it.itemId,null,NavOptions.Builder()
                                .setEnterAnim(android.R.anim.fade_in)
                                .setExitAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_in)
                                .build()
                            )

                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }
                    R.id.eventSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(it.itemId,null,NavOptions.Builder()
                                .setEnterAnim(android.R.anim.fade_in)
                                .setExitAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_in)
                                .build()
                            )
                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }
                    R.id.ticketSystemFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(it.itemId,null,NavOptions.Builder()
                                .setEnterAnim(android.R.anim.fade_in)
                                .setExitAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_in)
                                .build()
                            )
                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    R.id.calendarFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(it.itemId,null,NavOptions.Builder()
                                .setEnterAnim(android.R.anim.fade_in)
                                .setExitAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_in)
                                .build()
                            )
                        }, 300)

                        binding.drawerLayout.closeDrawer(GravityCompat.END)

                        true
                    }

                    R.id.permissionFragment -> {

                        Handler(Looper.getMainLooper()).postDelayed({

                            navController.navigate(it.itemId,null,NavOptions.Builder()
                                .setEnterAnim(android.R.anim.fade_in)
                                .setExitAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_in)
                                .build()
                            )
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

    fun setupTeaBoy() {

        binding.bottomNavigation.apply {
            menu.clear()
            inflateMenu(R.menu.teaboy_bottom_nav_menu)
            this.itemTextAppearanceActive = R.style.TeaBoyMenuItems
            this.itemTextAppearanceInactive = R.style.TeaBoyMenuItems

        }

        binding.navigationView.apply {
            menu.clear()
            inflateMenu(R.menu.teaboy_navigation_drawer)
        }

        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNavigation.setupWithNavController(navController)

            if(destination.id != R.id.loginFragment) {
                mainViewModel.getCount()
            }

            when (destination.id){

                R.id.teaBoyHomeFragment ->{
                    design(MainEnum.Home.main)

                }
                R.id.teaBoyOrdersFragment ->{
                    design(MainEnum.TeaBoyOrder.main)
                }
                R.id.qrCodeFragment -> {
                    design(MainEnum.Code.main)
                }
                R.id.loginFragment ->{
                    design(MainEnum.Login.main)

                }
                R.id.authenticationFragment -> {
                    design(MainEnum.Error.main)
                }

                R.id.permissionFragment -> {
                    design(MainEnum.Permission.main)
                }

                R.id.surveySystemFragment -> {
                    design(MainEnum.Survey.main)

                }

                R.id.calendarFragment -> {
                    design(MainEnum.Calendar.main)

                }


                R.id.ticketSystemFragment -> {
                    design(MainEnum.Ticket.main)

                }

                R.id.createTicketFragment -> {
                    design(MainEnum.CreateTicket.main)

                }

                R.id.eventSystemFragment -> {
                    design(MainEnum.Event.main)

                }

                R.id.eventDetailsFragment -> {
                    design(MainEnum.EventDetails.main)

                }

                R.id.ticketDetailsFragment -> {
                    design(MainEnum.TicketDetails.main)

                }

                R.id.blankFragment -> {
                    design(MainEnum.Blank.main)

                }

                R.id.notificationFragment -> {
                    design(MainEnum.Notification.main)
                }

                R.id.healthParentFragment -> {
                    design(MainEnum.Health.main)

                }
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)

        binding.navigationView.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.surveySystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(it.itemId,null,NavOptions.Builder()
                            .setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build()
                        )
                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                R.id.eventSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(it.itemId,null,NavOptions.Builder()
                            .setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build()
                        )
                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                R.id.ticketSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(it.itemId,null,NavOptions.Builder()
                            .setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build()
                        )
                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }

                R.id.calendarFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(it.itemId,null,NavOptions.Builder()
                            .setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build()
                        )
                    }, 300)

                    binding.drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }

                R.id.permissionFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigate(it.itemId,null,NavOptions.Builder()
                            .setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build()
                        )
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

        MainEnum.Login.main -> {
                window.statusBarColor = ContextCompat.getColor(this,R.color.white)
                binding.toolbar.visibility = View.GONE
                binding.customButton.visibility = View.GONE
                binding.customToolbar.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Home.main -> {
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.title.text = getString(R.string.tdf_oms)
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                            1)
                    }
                }
            }

            MainEnum.Refreshment.main -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            MainEnum.Menu.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.menu)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


            }

            MainEnum.UserOrder.main -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.order)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.TeaBoyOrder.main  -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.INVISIBLE
                binding.toolbar.visibility = View.VISIBLE
                binding.title.text = getString(R.string.order)
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            MainEnum.Favorite.main  ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.favorit)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Cart.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.cart)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

             MainEnum.Location.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.cart)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Code.main->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                binding.bottomNavigation.apply {
                    menu.setGroupCheckable(0,true,false)
                    menu.forEach {
                        it.isChecked = false
                    }
                    menu.setGroupCheckable(0,true,true)
                }

            }

            MainEnum.Reservation.main->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            MainEnum.CreateReservation.main->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.reservation)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

            MainEnum.Profile.main->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customToolbar.visibility = View.GONE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            MainEnum.Permission.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.leave)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Survey.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.survey)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Calendar.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.calendar)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Ticket.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.ticket)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Event.main ->{
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.event)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }
            MainEnum.EventDetails.main ->{
                window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                binding.customToolbar.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
                binding.customButton.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        }

            MainEnum.CreateTicket.main->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.addTicket)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.TicketDetails.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.ticketDetails)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Blank.main->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.black)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = ""
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Notification.main->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.notification)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            MainEnum.Health.main ->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.status)
                binding.customToolbar.background = ContextCompat.getDrawable(this,R.color.status)
                binding.customToolbar.visibility = View.VISIBLE
                binding.toolbar.visibility = View.INVISIBLE
                binding.customButton.visibility = View.GONE
                binding.customTitle.text = getString(R.string.healthData)
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }
        }
    }

    private fun observer() {
        mainViewModel.mainModel.observe(this){
            when (it) {
                is Result.Success -> {
                    if (it.data.unread_notification==0){
                        binding.bubble.visibility = View.GONE
                        binding.notificationNumber.visibility = View.GONE

                    }else{
                        binding.bubble.visibility = View.VISIBLE
                        binding.notificationNumber.visibility = View.VISIBLE
                        binding.bubble.bringToFront()
                        binding.notificationNumber.bringToFront()
                        binding.notificationNumber.text = it.data.unread_notification.toString()
                    }
                    if(it.data.pending_orders!=0) {
                        binding.bottomNavigation.getOrCreateBadge(R.id.refreshmentFragment).backgroundColor = ContextCompat.getColor(this,R.color.bubble)
                        binding.bottomNavigation.getOrCreateBadge(R.id.refreshmentFragment).number =
                            it.data.pending_orders!!

                        mainViewModel.orderCount(it.data.pending_orders!!)
                    }
                    else {
                        mainViewModel.orderCount(0)
                    }
                }

                is Result.Loading -> {}

                is Result.Error -> {
                    if (it.message == "Unauthorized") {
                        logoutFromFCM()
                        prefs.edit().clear().apply()
                        navController.navigate(R.id.loginFragment,null,NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.nav_graph,true, saveState = false)
                            .setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build()
                        )
                    }
                }
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        totalSteps = event?.values?.get(0)?.toInt()?:0
        previousSteps = prefs.getInt(PREVIOUS_STEPS,0)

        // when phone restart
        if(totalSteps<previousSteps || previousSteps==0){
            with(prefs.edit()) {
                putInt(PREVIOUS_STEPS, totalSteps)
            }.apply()

            previousSteps = prefs.getInt(PREVIOUS_STEPS,0)
        }

        with(prefs.edit()) {
            putInt(TOTAL_STEPS, totalSteps)
        }.apply()

        currentSteps = totalSteps - previousSteps

        mainViewModel.stepCount(currentSteps)

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun createPeriodTimeRequest(){

        val delay :Duration = Duration.between(LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0),LocalDateTime.now().withNano(0))

        var time = delay.toMinutes()

        time *= if (time<0){
            -1
        }else{
            1
        }

        val stepWorker : PeriodicWorkRequest = PeriodicWorkRequestBuilder<ResetStepsCountWorker>(24,TimeUnit.HOURS)
            .setInitialDelay(time,TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "send_periodic",ExistingPeriodicWorkPolicy.REPLACE,stepWorker
        )
    }

    inner class MyBroadCastReceiver:BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {

            try {
                getInstance()?.mainViewModel?.getCount()

            }catch (e:Exception){

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        reservationViewModel.deleteAll()
    }
}