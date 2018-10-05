package com.ericjohnson.footballapps.view.detailclub

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.support.v7.widget.Toolbar
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.FootballTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class DetailClubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val club: FootballTeam = intent.getParcelableExtra("club")
        DetailClubActivityUI(club).setContentView(this)
        val toolbar = find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    class DetailClubActivityUI(var club: FootballTeam) : AnkoComponent<DetailClubActivity> {
        override fun createView(ui: AnkoContext<DetailClubActivity>) = with(ui) {

            verticalLayout {
                background = ContextCompat.getDrawable(ctx, android.R.color.white)
                gravity = Gravity.CENTER_HORIZONTAL

                toolbar {
                    id = R.id.toolbar
                    elevation = dip(4).toFloat()
                    background = ContextCompat.getDrawable(ctx, R.color.colorPrimary)
                    setTitleTextColor(Color.WHITE)
                    title = club.name
                    navigationIcon = ContextCompat.getDrawable(ctx, R.drawable.ic_arrow_back)

                }.lparams(width = matchParent, height = wrapContent) {
                    bottomMargin = dip(16)
                }

                verticalLayout {
                    gravity = Gravity.CENTER_HORIZONTAL
                    padding = dip(16)

                    club.image?.let {
                        imageView(it) {

                        }.lparams(width = dip(128), height = dip(128)) {
                            bottomMargin = dip(16)
                        }
                    }

                    textView(club.name) {
                        textSize = 24F
                    }.lparams(width = wrapContent) {
                        bottomMargin = dip(16)
                    }

                    textView(club.desc) {
                        textSize = 14F
                    }.lparams(width = matchParent) {
                        bottomMargin = dip(16)
                    }
                }

            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


}
