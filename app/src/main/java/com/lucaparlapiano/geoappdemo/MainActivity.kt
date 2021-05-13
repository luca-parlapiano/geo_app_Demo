package com.lucaparlapiano.geoappdemo

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lucaparlapiano.geoappdemo.Constants.Companion.FINE_LOCATION
import com.lucaparlapiano.geoappdemo.model.LocationDetail
import com.lucaparlapiano.geoappdemo.viewModel.LocationViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    private lateinit var locationViewModel: LocationViewModel

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
               // R.id.poiListFragment, //-> it's because i must delete one step from navigation
                R.id.showPoiFragments
            ))
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        //Setting navigator in the bottombar
        navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null

        requestlocationUpgrade()

        //Appy Help Label  on topbar
        setupActionBarWithNavController(navController,appBarConfiguration)
    }


    private fun requestlocationUpgrade() {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            startTraker()
        }else{
            val permissionRequest = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.CAMERA)
            requestPermissions(permissionRequest,FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Constants.Companion.FINE_LOCATION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startTraker()
                }else{
                    Toast.makeText(this,"No GPS permission",Toast.LENGTH_LONG).show()
                }
            }
            Constants.Companion.CAMERA ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else{
                    Toast.makeText(this,"Check Camera permission",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startTraker() {
        locationViewModel.getLocationLiveData().observe(this, Observer {
            Log.d("Position Track User",it.latitude+" - "+it.longitude)
            locationViewModel.setActualPosition(LocationDetail(it.latitude,it.longitude))
        })

    }
}