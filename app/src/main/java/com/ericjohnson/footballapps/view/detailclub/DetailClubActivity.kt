package com.ericjohnson.footballapps.view.detailclub

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.model.FootballTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.system.exitProcess

class DetailClubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val club: FootballTeam = intent.getParcelableExtra("club")
        DetailClubActivityUI(club).setContentView(this)
        val toolbar = find<Toolbar>(R.id.toolbar)
        setActionBar(toolbar)
    }

    class DetailClubActivityUI(var club: FootballTeam) : AnkoComponent<DetailClubActivity> {
        override fun createView(ui: AnkoContext<DetailClubActivity>) = with(ui) {

            verticalLayout {
                background = ContextCompat.getDrawable(ctx, android.R.color.white)
                gravity = Gravity.CENTER_HORIZONTAL

                toolbar {
                    id = R.id.toolbar
                    title = club.name
                    elevation = dip(4).toFloat()
                    background = ContextCompat.getDrawable(ctx, R.color.colorPrimary)
                    setTitleTextColor(Color.WHITE)
                    //navigationIcon = ContextCompat.getDrawable(ctx, R.drawable.ic_arrow_back)
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


}
