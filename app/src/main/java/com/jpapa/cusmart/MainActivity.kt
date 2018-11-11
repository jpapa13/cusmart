package com.jpapa.cusmart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.facebook.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import com.jpapa.cusmart.R.layout.nav_header_main
import com.facebook.GraphResponse
import org.json.JSONObject
import com.facebook.GraphRequest




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var callbackManager:CallbackManager? = null
    private var profileTracker:ProfileTracker? = null
    private var accessToken: AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        callbackManager = CallbackManager.Factory.create()


        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setReadPermissions("email")
        // If using in a fragment
        //loginButton.setFragment(this)


        //MODIFICAR ESTA LINEA, PARA CAMBIARLA AL HEADER DE LA BARRA
       // val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
     //   val vi = inflater.inflate(R.layout.nav_header_main, null) //log.xml is your file.
     //   val tvusuario = vi.findViewById<TextView>(R.id.tvusuario) //get a reference to the textview on the log.xml file.
        val tvNombre = findViewById<TextView>(R.id.tvNombre)

        if(AccessToken.getCurrentAccessToken()!= null && Profile.getCurrentProfile() != null){
            accessToken = AccessToken.getCurrentAccessToken()
            tvNombre.text = Profile.getCurrentProfile().firstName + " " + Profile.getCurrentProfile().lastName

            cargarFoto()
        }

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired

                tvNombre.setText(Profile.getCurrentProfile().name)
                //Log.d("ACCESS-TOKEN",accessToken.token)

                if(Profile.getCurrentProfile() == null){
                    profileTracker = object:ProfileTracker(){
                        override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                            Log.d("NOMBRE", currentProfile?.firstName+" "+ currentProfile?.lastName)
                            profileTracker!!.startTracking()
                        }

                    }
                }else{
                    val profile = Profile.getCurrentProfile()
                    Log.d("NOMBRE", profile?.firstName)
                    tvNombre.setText(profile?.firstName+" "+ profile?.lastName)
                }
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun cargarFoto(){
        val request = GraphRequest.newMeRequest(this.accessToken) { objeto, response ->
            // Application code
            Log.d("GRAPH",response.toString())
        }
        val parameters = Bundle()
        parameters.putString("fields", "picture")
        request.parameters = parameters
        request.executeAsync()
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
                startActivity(intent)
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_cucea -> {

            }
            R.id.nav_cuaad -> {

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
