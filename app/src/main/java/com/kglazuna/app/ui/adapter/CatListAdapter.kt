package com.kglazuna.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kglazuna.app.R
import com.kglazuna.app.model.Cat
import timber.log.Timber

class CatListAdapter(
    private val context: Context,
    var catList: List<Cat>,
    private val listener: OnCatClickListener? = null
) :
    RecyclerView.Adapter<CatListAdapter.ViewHolder>() {

    interface OnCatClickListener {
        fun onCatSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.d("onCreateViewHolder")
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cat, parent, false)

        return ViewHolder(rootView, context, listener)
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount")
        return catList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Timber.d("onBindViewHolder")

        holder.bind(catList[position])
    }

    class ViewHolder(
        itemView: View,
        private val context: Context,
        private val listener: OnCatClickListener? = null
    ) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var catImageView: ImageView = itemView.findViewById(R.id.catItemView)
        init {
            listener?.let { itemView.setOnClickListener(this) }
        }

        fun bind(cat: Cat) {
            Timber.d("adapter binding cat")
            Glide.with(context)
                .load(cat.url)
                .placeholder(R.drawable.ic_cat_placeholder)
                .into(catImageView)
        }

        override fun onClick(v: View?) {
            listener?.onCatSelected(adapterPosition)
        }
    }
}
