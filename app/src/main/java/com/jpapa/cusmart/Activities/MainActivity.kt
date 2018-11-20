package com.jpapa.cusmart.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.jpapa.cusmart.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Inicia sesión con Facebook", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_cucei -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUCEI")
               // intent.putExtra("cu2", "@string/qci_info")
                startActivity(intent)
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_cucea -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUCEA")
               // intent.putExtra("cu2", "cucea_info")
               // intent.putExtra("img",R.drawable.ic_cucea)
                startActivity(intent)
            }
            R.id.nav_cuaad -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUAAD")
                startActivity(intent)
            }
            R.id.nav_cucsh -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUCSH")
                startActivity(intent)
            }
            R.id.nav_cucba -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUCBA")
                startActivity(intent)
            }
            R.id.nav_cucs -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUCS")
                startActivity(intent)
            }
            R.id.nav_cut -> {
                val intent = Intent(this, CUActivity::class.java)
                intent.putExtra("cu", "CUT")
                startActivity(intent)
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
