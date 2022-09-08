package com.wbconcept.myapplication.Activities

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.wbconcept.myapplication.BroadcastReceiver.ConnectivityReceiver
import com.wbconcept.myapplication.R
import dagger.hilt.android.AndroidEntryPoint
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : FragmentActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
     private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )


        profil.setOnClickListener({
            val intent = Intent(this, Profile_Details::class.java)
            startActivity(intent)
        })

        val navigationController = Navigation.findNavController(this, R.id.newsNavHostFragment)
        ExpandableBottomBarNavigationUI.setupWithNavController(
            expandable_bottom_bar,
            navigationController
        )

    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar = Snackbar.make(
                findViewById(R.id.rootLayout),
                "You are offline",
                Snackbar.LENGTH_LONG
            ) //Assume "rootLayout" as the root layout of every activity.
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        ForegroundService.stopService(this)
//
//       // ForegroundService.startService(this, "Foreground Service is running...")
//
//    }
//    override fun onStop() {
//        super.onStop()
//
//        ForegroundService.startService(this, "Foreground Service is running...")
//
//    }


}