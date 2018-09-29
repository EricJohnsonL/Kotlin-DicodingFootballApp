package com.ericjohnson.footballapps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.model.FootballTeam
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_football_team.view.*

/**
 * Created by johnson on 29/09/18.
 */

class ListFootballClubAdapter(private val context: Context, private val datas: List<FootballTeam>,
                              private var listener: (FootballTeam) -> Unit) :
        RecyclerView.Adapter<ListFootballClubAdapter.ListFootballViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ListFootballViewHolder(LayoutInflater.from(context).inflate(R.layout.item_football_team,
                    parent, false))

    override fun getItemCount(): Int = datas.size


    override fun onBindViewHolder(holder: ListFootballViewHolder, position: Int) {
        holder.bindItem(datas[position], listener)
    }

    class ListFootballViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bindItem(datas: FootballTeam, listener: (FootballTeam) -> Unit) {
            itemView.tv_club_name.text = datas.name
            Glide.with(itemView.context).load(datas.image).into(itemView.img_club)

            itemView.setOnClickListener {
                listener(datas)
            }
        }
    }


}
