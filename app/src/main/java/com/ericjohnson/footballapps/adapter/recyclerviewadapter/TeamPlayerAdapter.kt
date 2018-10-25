package com.ericjohnson.footballapps.adapter.recyclerviewadapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.PlayerDetail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_player.*

/**
 * Created by johnson on 10/23/18.
 */
class TeamPlayerAdapter(private val context: Context, private var datas: MutableList<PlayerDetail>) :
        RecyclerView.Adapter<TeamPlayerAdapter.TeamPlayerViewHolder>() {

    lateinit var clickListener: ((PlayerDetail) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayerAdapter.TeamPlayerViewHolder =
            TeamPlayerAdapter.TeamPlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))

    override fun getItemCount(): Int = datas.size

    fun addAllItem(items: MutableList<PlayerDetail>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItem() {
        datas.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TeamPlayerViewHolder, position: Int) {
        holder.bind(context, datas[position], clickListener)
    }

    class TeamPlayerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(context: Context, datas: PlayerDetail, listener: (PlayerDetail) -> Unit) {

            when {
                adapterPosition % 2 == 0 -> ll_team_player.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_white)
                else -> ll_team_player.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_ripple_grey)
            }

            val requestOptions = RequestOptions()
                    .error(R.drawable.ic_image)
                    .fallback(R.drawable.ic_image)

            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(datas.strCutout)
                    .listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            pb_player.visibility = View.GONE
                            return false
                        }

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            pb_player.visibility = View.GONE
                            return false
                        }

                    })
                    .into(img_player)
            tv_player_name.text = datas.strPlayer
            tv_position.text = datas.strPosition

            itemView.setOnClickListener {
                listener(datas)
            }
        }
    }
}