package com.ali.filmgokino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.ali.filmgokino.TinyDB
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.show_image.*
import java.util.ArrayList

class ShowImage : AppCompatActivity() {

    lateinit var tinydb: TinyDB
    lateinit var list: ArrayList<String>
    var q: Boolean = false
    var b: Boolean = false
    var count: Int = 1
    lateinit var VideoId: String

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_image)

        MobileAds.initialize(this,
            getString(R.string.ReklamaPop))
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.BANNER)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        tinydb = TinyDB(this)
        list = tinydb.getListString("nameid")

        count=tinydb.getInt("count")

        VideoId = intent.getStringExtra("id")
        var VideoImage = intent.getStringExtra("image")
        var VideoTime = intent.getStringExtra("time")
        var VideoTitle = intent.getStringExtra("title")
        var VideoDescription = intent.getStringExtra("description")
        var VideoDuration = intent.getStringExtra("duration")

        Glide.with(this).load(VideoImage).centerCrop().into(ImageShow)
        TitleShow.text = "   " + VideoTitle
        TimeShow.text = VideoDuration
        Time1Show.text = VideoTime.substring(0, 10)
        DataShow.text = VideoDescription
        ImageShow.setOnClickListener {
            var i = Intent(this, ShowVideo::class.java)
            i.putExtra("id", VideoId)
            startActivity(i)
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar_show_imagee)
        toolbar.setNavigationIcon(R.drawable.back_black)
        val bar = supportActionBar
        bar?.setDisplayHomeAsUpEnabled(true)
        bar?.setHomeButtonEnabled(true)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = VideoTitle

        q = false
        list.forEach {
            if (it.equals(VideoId)) {
                q = true
            }
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        if (count == 3) {
            count = 0
            tinydb.putInt("count", count)
            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    ShowRek()
                }

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.show_image_menu, menu)

        if (q) {
            menu.getItem(0).icon = getDrawable(R.drawable.avorite_black)
            b = true
        } else {
            b = false
            menu.getItem(0).icon = getDrawable(R.drawable.favorite_border_black)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.action_love -> {

                if (q) {
                    item.icon = getDrawable(R.drawable.favorite_border_black)
                    Snackbar.make(toolbar_show_imagee, "Удалено", Snackbar.LENGTH_LONG).show()
                    q = false
                } else {
                    item.icon = getDrawable(R.drawable.avorite_black)
                    Snackbar.make(toolbar_show_imagee, "Добавлено", Snackbar.LENGTH_LONG).show()
                    q = true
                }
            }
            R.id.action_share -> {
                 ShareF()
            }
            else -> super.onOptionsItemSelected(item)
        }
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


    fun ShowRek() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Toast.makeText(this@ShowImage, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        if (!((b == true && q == true) || (b == false && q == false))) {
            if (b == true && q == false) {
                list.remove(VideoId)
            } else {
                list.add(VideoId)
            }
            tinydb.putListString("nameid", list)
        }
        count++
        tinydb.putInt("count",count)
        finish()
    }
}
