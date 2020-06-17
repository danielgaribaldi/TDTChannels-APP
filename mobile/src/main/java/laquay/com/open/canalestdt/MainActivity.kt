package laquay.com.open.canalestdt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import laquay.com.open.canalestdt.controller.SharedPreferencesController

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.nav_tv_channels -> loadTVFragment()
        }

        true
    }

    private fun loadTVFragment() = supportFragmentManager.beginTransaction().apply {
        replace(R.id.content_frame, TVFragment.newInstance(), TVFragment.TAG)
        commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        SharedPreferencesController.init(this)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navView.selectedItemId = R.id.nav_tv_channels

        loadTVFragment()

    }
}