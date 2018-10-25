package com.ericjohnson.footballapps.adapter.recyclerviewadapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.utils.TimeUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*

/**
 * Created by johnson on 04/10/18.
 */

class MatchScheduleAdapter(private val context: Context, private var datas: MutableList<MatchDetail>,
                           private val scheduleType: String) :
        RecyclerView.Adapter<MatchScheduleAdapter.MatchScheduleViewHolder>() {

    lateinit var clickListener: ((MatchDetail) -> Unit)

    lateinit var reminderListener: ((MatchDetail) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MatchScheduleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))


    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: MatchScheduleViewHolder, position: Int) {
        holder.bind(datas[position], scheduleType, clickListener, reminderListener)
    }

    fun addAllItem(items: MutableList<MatchDetail>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItem() {
        datas.clear()
        notifyDataSetChanged()
    }

    class MatchScheduleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(datas: MatchDetail, scheduleType: String, listener: (MatchDetail) -> Unit, reminderListener: (MatchDetail) -> Unit) {

            when {
                adapterPosition % 2 == 0 -> ll_schedule.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_white)
                else -> ll_schedule.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_grey)
            }

            when (scheduleType) {
                ScheduleType.PREVIOUS_EVENT -> btn_reminder.visibility = View.GONE
            }

            val dateTime = TimeUtil.getDateTime(datas.dateEvent.toString() + " "
                    + datas.strTime.toString()).split("-")

            tv_date.text = dateTime[0]
            tv_time.text = dateTime[1]
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

            btn_reminder.setOnClickListener {
                reminderListener(datas)
            }

        }
    }

}