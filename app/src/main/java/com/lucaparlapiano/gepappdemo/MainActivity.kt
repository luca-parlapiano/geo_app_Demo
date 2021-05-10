package com.lucaparlapiano.gepappdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //private lateinit var pViewModel: poiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

       /* window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
            )*/

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting fragments label in topbar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.poiHomeFragment,
                R.id.poiListFragment,
                R.id.showPoiFragments
            ))

        //Setting navigator in the bottombar
        var navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null

        //Appy Label  on topbar
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
}