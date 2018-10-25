package com.ericjohnson.footballapps.adapter.recyclerviewadapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.db.FavoriteTeam
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team.*

/**
 * Created by johnson on 10/23/18.
 */
class FavoriteTeamAdapter(private val context: Context, private var datas: MutableList<FavoriteTeam>,
                          private val listener: (FavoriteTeam) -> Unit) :
        RecyclerView.Adapter<FavoriteTeamAdapter.FavoriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FavoriteTeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bind(datas[position], listener)
    }

    fun addAllItem(items: MutableList<FavoriteTeam>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    class FavoriteTeamViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(datas: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            when {
                adapterPosition % 2 == 0 -> ll_team_list.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_white)
                else -> ll_team_list.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_grey)
            }

            val requestOptions = RequestOptions()
                    .error(R.drawable.ic_image)
                    .fallback(R.drawable.ic_image)

            Glide.with(itemView.context).setDefaultRequestOptions(requestOptions)
                    .load(datas.image).into(img_team_badge)
            tv_team_name.text = datas.name

            itemView.setOnClickListener {
                listener(datas)
            }
        }
    }
}