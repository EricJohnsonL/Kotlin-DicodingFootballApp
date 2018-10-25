package com.ericjohnson.footballapps.adapter.recyclerviewadapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.utils.TimeUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*

/**
 * Created by johnson on 10/10/18.
 */

class FavoriteMatchAdapter(private val context: Context, private var datas: MutableList<FavoriteMatch>,
                           private val listener: (FavoriteMatch) -> Unit) :
        RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FavoriteMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))


    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: FavoriteMatchViewHolder, position: Int) {
        holder.bind(datas[position], listener)
    }

    fun addAllItem(items: MutableList<FavoriteMatch>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    class FavoriteMatchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(datas: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {

            when {
                adapterPosition % 2 == 0 -> ll_schedule.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_white)
                else -> ll_schedule.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_grey)
            }

            btn_reminder.visibility = View.GONE

            val dateTime = TimeUtil.getDateTime(datas.date.toString() + " "
                    + datas.time.toString()).split("-")

            tv_date.text = dateTime[0]
            tv_time.text = dateTime[1]
            tv_home_team.text = datas.homeTeam
            tv_away_team.text = datas.awayTeam
            tv_home_score.text = when {
                datas.homeScore == null -> "-"
                else -> datas.homeScore.toString()
            }

            tv_away_score.text = when {
                datas.awayScore == null -> "-"
                else -> datas.awayScore.toString()
            }

            itemView.setOnClickListener {
                listener(datas)
            }

        }
    }

}