package com.ericjohnson.footballapps.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.utils.TimeUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*

/**
 * Created by johnson on 04/10/18.
 */

class MatchScheduleAdapter(private val context: Context, private var datas: MutableList<MatchDetail>,
                           private val listener: (MatchDetail) -> Unit) :
        RecyclerView.Adapter<MatchScheduleAdapter.MatchScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MatchScheduleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))


    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: MatchScheduleViewHolder, position: Int) {
        holder.bind(datas[position], listener)
    }

    fun addAllItem(items: MutableList<MatchDetail>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    class MatchScheduleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(datas: MatchDetail, listener: (MatchDetail) -> Unit) {

            when {
                adapterPosition % 2 == 0 -> ll_schedule.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_white)
                else -> ll_schedule.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_grey)
            }

            tv_date.text = TimeUtil.getFormattedDate(datas.dateEvent.toString())
            tv_home_team.text = datas.strHomeTeam
            tv_away_team.text = datas.strAwayTeam
            tv_home_score.text = when {
                datas.intHomeScore == null -> "-"
                else -> datas.intHomeScore.toString()
            }

            tv_away_score.text = when {
                datas.intAwayScore == null -> "-"
                else -> datas.intAwayScore.toString()
            }

            itemView.setOnClickListener {
                listener(datas)
            }

        }
    }

}