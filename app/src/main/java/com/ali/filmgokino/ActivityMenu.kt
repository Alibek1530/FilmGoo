package com.ali.filmgokino

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.app_bar_activity_menu.*
import kotlinx.android.synthetic.main.dialog_info.view.*

class ActivityMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var listVideo: ArrayList<ArrayList<ModelVideo>>
    lateinit var listName: ArrayList<String>
    lateinit var listNameZak: ArrayList<ModelVideo>

    lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        listVideo = intent.getSerializableExtra("kk") as ArrayList<ArrayList<ModelVideo>>
        listName = intent.getStringArrayListExtra("name") as ArrayList<String>
        listNameZak = intent.getSerializableExtra("nameZak") as ArrayList<ModelVideo>

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_home)
        supportFragmentManager.beginTransaction()
            .replace(R.id.oyna, NewFragment(listVideo, listName)).commit()
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.activity_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                openDialog()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                toolbar.title = "Главная"
                supportFragmentManager.beginTransaction()
                    .replace(R.id.oyna, NewFragment(listVideo, listName)).commit()
            }
            R.id.nav_zakladka -> {
                toolbar.title = "Закладки"
                supportFragmentManager.beginTransaction().replace(R.id.oyna, Zakladka(listNameZak))
                    .commit()
            }
            R.id.nav_otsenit -> {
                MarketStar()
            }
            R.id.nav_share -> {
                ShareF()
            }
            R.id.nav_kon -> {
                toolbar.title = "Политика"
                supportFragmentManager.beginTransaction().replace(R.id.oyna, Politika()).commit()
            }
            R.id.nav_info -> {
                openDialog()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun ShareF() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.shareApp))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun openDialog() {
        var dialog = LayoutInflater.from(this).inflate(R.layout.dialog_info, null)
        var bulder = this?.let {
            AlertDialog.Builder(it).setView(dialog)
        }
        var mDialog = bulder?.show()
        mDialog?.setCancelable(false)

        dialog.DialogOk.setOnClickListener {
            mDialog!!.dismiss()
        }
    }

    fun MarketStar() {
        var intent = Intent(Intent.ACTION_VIEW)
        // baholash google playda
        intent.setData(Uri.parse(getString(R.string.urlPlayApp)))
        startActivity(intent)
    }
}
