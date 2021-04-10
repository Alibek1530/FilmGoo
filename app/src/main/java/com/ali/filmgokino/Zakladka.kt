package com.ali.filmgokino


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.fragment_zakladka.*
import java.util.ArrayList


class Zakladka(var list: ArrayList<ModelVideo>) : Fragment(), DataZak {

    lateinit var listZakladka: ArrayList<ModelVideo>

    override fun datazak(name: String, nameid: String) {
        openDialog(name, nameid)
    }

    override fun datazak(pos: Int) {
        var i = Intent(context, ShowImage::class.java)
        i.putExtra("id", listZakladka.get(pos).id)
        i.putExtra("title", listZakladka.get(pos).title)
        i.putExtra("time", listZakladka.get(pos).time)
        i.putExtra("image", listZakladka.get(pos).image)
        i.putExtra("description", listZakladka.get(pos).description)
        i.putExtra("duration", listZakladka.get(pos).duration)
        startActivity(i)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zakladka, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Adapterrr()
    }

    private fun Adapterrr() {
        listZakladka = arrayListOf()
        var tinydb = TinyDB(this.context)
        var listDB = tinydb.getListString("nameid")
        if (tinydb != null) {
            listDB.forEach {
                var db = it
                list.forEach {
                    if (db.equals(it.id)) {
                        listZakladka.add(it)
                    }
                }
            }
        }


        var adapterVer = MyAdapterVerZak(listZakladka, this)
        RecyclerZak.setHasFixedSize(true)
        RecyclerZak.isNestedScrollingEnabled = true
        RecyclerZak.layoutManager = LinearLayoutManager(context)
        RecyclerZak.adapter = adapterVer
    }

    fun openDialog(name: String, nameid: String) {

        var dialog = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        var bulder = context?.let {
            AlertDialog.Builder(it).setView(dialog)
        }
        var mDialog = bulder?.show()

        var text = "Удалить из Закладок"
        var textDab = "Добавить в Закладки"
        var q = false
        var tinydb = TinyDB(context)
        var list = arrayListOf<String>()
        if (tinydb != null) {
            list = tinydb.getListString("nameid")
            list.forEach {
                if (nameid.equals(it)) {
                    q = true
                }
            }

        }
        if (q) {
            dialog.Dialog_Izb.text = text
        } else {
            dialog.Dialog_Izb.text = textDab
        }

        dialog.Dialog_Title.text = "  " + name

        dialog.Dialog_Izb.setOnClickListener {
            mDialog!!.dismiss()
            if (q) {
                list.remove(nameid)
                Snackbar.make(view!!.rootView, "Удалено", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                list.add(nameid)
                Snackbar.make(view!!.rootView, "Добавлено", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            tinydb.putListString("nameid", list)

            Adapterrr()
        }

        dialog.Dialog_Share.setOnClickListener {
            mDialog!!.dismiss()
            ShareF()
        }

    }

    fun ShareF() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/send")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
