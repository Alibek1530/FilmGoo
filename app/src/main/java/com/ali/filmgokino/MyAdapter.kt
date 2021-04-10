package com.ali.filmgokino


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item.view.*

class MyAdapter(var list: List<ModelVideo>, var main: FragmentMain) : RecyclerView.Adapter<MyAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false), main)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list.get(position))
    }


    class Holder(itemView: View, main: FragmentMain) : RecyclerView.ViewHolder(itemView) {


        init {
            itemView.setOnClickListener {

                main.data(adapterPosition)
            }
        }

        fun bind(model: ModelVideo) {

            val radius = itemView.context.resources.getDimensionPixelSize(R.dimen.corner_radius)
            Glide.with(itemView.context)
                .load(model.image).centerInside()
                .transform(RoundedCorners(radius))
                .into(itemView.image)
            itemView.title.text = model.title
            itemView.time.text = model.duration
            itemView.timee.text = model.viewCount
            itemView.timeew.text = model.likeCount
        }


    }
}