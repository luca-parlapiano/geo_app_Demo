package com.lucaparlapiano.geoappdemo

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lucaparlapiano.geoappdemo.Constants.CAMERA
import com.lucaparlapiano.geoappdemo.Constants.FINE_LOCATION
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
               // R.id.poiListFragment,
                R.id.showPoiFragments
            ))

        //Setting navigator in the bottombar
        var navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null
        //checkForPermission(android.Manifest.permission.CAMERA,"camera",CAMERA)
        checkForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,"location",FINE_LOCATION)
        //Appy Label  on topbar
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    //Permission Section
     fun checkForPermission(permission:String,name:String,requestCode:Int){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            when {
                ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext,"$name "+getString(R.string.permission_ok),Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission,name,requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if(grantResults.isEmpty() || grantResults[0] !=  PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"$name "+getString(R.string.permission_no),Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,"$name "+getString(R.string.permission_ok), Toast.LENGTH_SHORT).show()
            }
        }

        when(requestCode){
            Constants.FINE_LOCATION -> innerCheck("location")
            Constants.CAMERA ->innerCheck("camera")
            Constants.EXTERNAL_READ -> innerCheck("external_read")
        }
    }


    private fun showDialog(permission: String,name: String,requestCode: Int){
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage(getString(R.string.permissione_question_1)+" $name "+getString(R.string.permissione_question_2))
            setTitle(getString(R.string.permission_required))
            setPositiveButton(getString(R.string.permission_ok_button)){ dialog,  which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
        }
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }
}