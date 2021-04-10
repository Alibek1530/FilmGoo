package com.ali.filmgokino


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.filmgokino.ModelVideo
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.ArrayList


class FragmentMain() : Fragment(), Data {
    override fun data(pos: Int) {
        var a = list.size - pos - 1
        var i = Intent(context, ShowImage::class.java)
        i.putExtra("id", list.get(a).id)
        i.putExtra("title", list.get(a).title)
        i.putExtra("time", list.get(a).time)
        i.putExtra("image", list.get(a).image)
        i.putExtra("description", list.get(a).description)
        i.putExtra("duration", list.get(a).duration)
        startActivity(i)
    }


    lateinit var list: ArrayList<ModelVideo>

    lateinit var listHor: ArrayList<ModelVideo>
    lateinit var listVer: ArrayList<ModelVideo>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = arguments!!.getSerializable("data") as ArrayList<ModelVideo>
        listHor = arrayListOf()
        listVer = arrayListOf()

        if (list.size >= 4) {
            for (i in (list.size - 1) downTo (list.size - 4)) {
                listHor.add(list.get(i))
            }
            for (i in (list.size - 5) downTo 0) {
                listVer.add(list.get(i))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adapterVer = MyAdapterVer(listHor, listVer, this)
        RecyclerVer.setHasFixedSize(true)
        RecyclerVer.isNestedScrollingEnabled = true
        RecyclerVer.layoutManager = LinearLayoutManager(context)
        RecyclerVer.adapter = adapterVer
    }
}
