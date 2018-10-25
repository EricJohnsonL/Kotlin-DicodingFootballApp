package com.ericjohnson.footballapps.view.playerdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.PlayerDetail
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private val presenter: IPlayerDetailPresenter<PlayerDetailView> = PlayerDetailPresenter()

    private lateinit var playerId: String

    private lateinit var playerName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        onAttachView()
        initIntent()
        setupToolbar()
        requestPlayerDetail()
        btn_retry.setOnClickListener {
            requestPlayerDetail()
        }
    }

    private fun initIntent() {
        playerId = intent.getStringExtra(AppConstants.KEY_PLAYER_ID)
        playerName = intent.getStringExtra(AppConstants.KEY_PLAYER_NAME)
    }


    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = playerName
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun requestPlayerDetail() {
        EspressoIdlingResource.increment()
        presenter.getPlayerDetail(playerId)
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProgressBar(isShown: Boolean) = when {
        isShown -> pb_player_detail.visibility = View.VISIBLE
        else -> pb_player_detail.visibility = View.GONE
    }

    override fun showPlayerDetail(isShown: Boolean) = when {
        isShown -> sv_player_detail.visibility = View.VISIBLE
        else -> sv_player_detail.visibility = View.GONE
    }

    override fun setPlayerDetail(playerDetail: PlayerDetail) {
        val requestOptions = RequestOptions()
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image)

        Glide.with(this).setDefaultRequestOptions(requestOptions)
                .load(playerDetail.strThumb)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        pb_player_banner.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        pb_player_banner.visibility = View.GONE
                        return false
                    }

                })
                .into(img_player)
        tv_natioanlity.text = playerDetail.strNationality
        tv_position.text = playerDetail.strPosition
        tv_height.text = when {
            TextUtils.isEmpty(playerDetail.strHeight) -> "-"
            else -> playerDetail.strHeight
        }
        tv_weight.text = when {
            TextUtils.isEmpty(playerDetail.strWeight) -> "-"
            else -> playerDetail.strWeight
        }
        tv_overview.text = playerDetail.strDescriptionEN
    }


    override fun showErrorView(isShown: Boolean) = when {
        isShown -> ev_error_team_player.visibility = View.VISIBLE
        else -> ev_error_team_player.visibility = View.GONE
    }


    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }
}
