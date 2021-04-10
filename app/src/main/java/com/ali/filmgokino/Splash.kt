package com.ali.filmgokino


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import kotlin.collections.ArrayList


class Splash : AppCompatActivity() {

    lateinit var listVideo: ArrayList<ModelVideo>
    lateinit var listVideoEsc: ArrayList<ModelVideo>
    lateinit var listName: ArrayList<String>
    lateinit var listDATA: java.util.ArrayList<java.util.ArrayList<ModelVideo>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout._splash)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("tv")

        listVideo = arrayListOf()
        listVideoEsc = arrayListOf()
        listName = arrayListOf()
        listDATA = arrayListOf()

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach {
                   listName.add(it.key.toString())
                       listVideo= arrayListOf()
                    it.children.forEach {
                        val model = it.getValue(ModelVideo::class.java)
                        listVideo.add(model!!)
                        listVideoEsc.add(model!!)
                    }
                    listDATA.add(listVideo)
                }
                Inten()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Splash, "Нет соединение", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun Inten() {
        var i = Intent(this, ActivityMenu::class.java)
        i.putExtra("kk", listDATA)
        i.putExtra("name",listName)
        i.putExtra("nameZak",listVideoEsc)
        startActivity(i)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}

