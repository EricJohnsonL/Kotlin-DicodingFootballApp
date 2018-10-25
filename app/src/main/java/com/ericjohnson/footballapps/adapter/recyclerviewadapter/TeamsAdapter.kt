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
import com.ericjohnson.footballapps.data.api.TeamList
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team.*

/**
 * Created by johnson on 10/22/18.
 */
class TeamsAdapter(private val context: Context, private var datas: MutableList<TeamList>) :
        RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    lateinit var clickListener: ((TeamList) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder =
            TeamsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))

    override fun getItemCount(): Int = datas.size

    fun addAllItem(items: MutableList<TeamList>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItem() {
        datas.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bind(context, datas[position], clickListener)
    }

    class TeamsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(context: Context, datas: TeamList, listener: (TeamList) -> Unit) {

            when {
                adapterPosition % 2 == 0 -> ll_team_list.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_white)
                else -> ll_team_list.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_grey)
            }

            val requestOptions = RequestOptions()
                    .error(R.drawable.ic_image)
                    .fallback(R.drawable.ic_image)

            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(datas.strTeamBadge).into(img_team_badge)

            tv_team_name.text = datas.strTeam

            itemView.setOnClickListener {
                listener(datas)
            }
        }
    }
}