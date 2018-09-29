package com.ericjohnson.footballapps.view.mainactivity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.ListFootballClubAdapter
import com.ericjohnson.footballapps.model.FootballTeam
import com.ericjohnson.footballapps.view.detailclub.DetailClubActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    private var items: MutableList<FootballTeam> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI(initData()).setContentView(this)

    }

    class MainActivityUI(var items: MutableList<FootballTeam>) : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

            recyclerView{
                backgroundColor = Color.WHITE
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
                adapter = ListFootballClubAdapter(ctx, items) {
                    startActivity<DetailClubActivity>("club" to it)
                }
            }
        }

    }

    private fun initData(): MutableList<FootballTeam> {
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val desc = resources.getStringArray(R.array.club_description)
        items.clear()
        for (i in name.indices) {
            items.add(FootballTeam(name[i], desc[i], image.getResourceId(i, 0)))
        }
        image.recycle()
        return items
    }
}
