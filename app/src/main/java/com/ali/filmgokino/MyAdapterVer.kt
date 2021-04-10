package com.ali.filmgokino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_grid.view.*
import kotlinx.android.synthetic.main.item_ver.view.*
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.ali.filmgokino.TinyDB
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog.view.*


class MyAdapterVer(
    var dataHorizontal: List<ModelVideo>,
    var dataVertical: List<ModelVideo>,
    var mContext: FragmentMain
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var TYPE_HEADER = 0
    var TYPE_ITEM = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
            return MyViewHolder(v, mContext)
        } else if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ver, parent, false)
            return MyViewHolderHeader(v)
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val model = dataVertical.get(position - 1)
            holder.bind(model)
        } else if (holder is MyViewHolderHeader) {
            holder
        }
    }

    override fun getItemCount(): Int {
        return dataVertical.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) {
            TYPE_HEADER
        } else{
            TYPE_ITEM
        }

    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private inner class MyViewHolder(var v: View, var main: FragmentMain) : RecyclerView.ViewHolder(v) {
        init {
            itemView.setOnClickListener {

                main.data(adapterPosition + 3)
            }
        }

        fun bind(model: ModelVideo) {
            v.row_tv_title.text = model.title
            val radius = v.context.resources.getDimensionPixelSize(R.dimen.corner_radius_mini)
            Glide.with(v.context)
                .load(model.image).centerCrop()
                .transform(RoundedCorners(radius))
                .into(v.row_img)
            v.row_tv_discription.text = model.description
            v.row_tv_additional_info.text = model.duration
            v.row_tv_additional_infoo.text = model.likeCount

            v.Menu_Btn.setOnClickListener {
                openDialog(model.title, model.id)
            }

        }

        fun openDialog(name: String, nameid: String) {

            var dialog = LayoutInflater.from(v.context).inflate(R.layout.dialog, null)
            var bulder = v.context?.let {
                AlertDialog.Builder(it).setView(dialog)
            }
            var mDialog = bulder?.show()
            var text = "Удалить из Закладок"
            var textDab = "Добавить в Закладки"
            var q = false
            var tinydb = TinyDB(v.context)
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
                    Snackbar.make(v, "Удалено", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                } else {
                    list.add(nameid)
                    Snackbar.make(v, "Добавлено", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                tinydb.putListString("nameid", list)

            }

            dialog.Dialog_Share.setOnClickListener {
                ShareF()
            }

        }

        fun ShareF() {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.shareApp))
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            mContext.startActivity(shareIntent)
        }
    }

    private inner class MyViewHolderHeader(v: View) : RecyclerView.ViewHolder(v) {


        init {
            val linnerVer = LinearLayoutManager.HORIZONTAL
            v.RecyclerHor.layoutManager = LinearLayoutManager(v.context, linnerVer, false)
            v.RecyclerHor.adapter = MyAdapter(dataHorizontal, mContext)

        }

    }
}