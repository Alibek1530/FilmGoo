package com.ali.filmgokino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_grid.view.*


class MyAdapterVerZak(
    var dataVertical: ArrayList<ModelVideo>, var mContext: Zakladka
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return dataVertical.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val model = dataVertical.get(position)
            holder.bind(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //inflate your layout and pass it to view holder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return MyViewHolder(v, mContext)
    }

    class MyViewHolder(var v: View, var main: Zakladka) : RecyclerView.ViewHolder(v) {
        init {
            itemView.setOnClickListener {
                main.datazak(adapterPosition)
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
                main.datazak(model.title, model.id)
            }
        }

    }

}