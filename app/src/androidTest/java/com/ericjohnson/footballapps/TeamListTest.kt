package com.ericjohnson.footballapps

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.utils.EspressoIdlingResource
import com.ericjohnson.footballapps.view.mainactivity.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf


/**
 * Created by johnson on 10/26/18.
 */
class TeamListTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun testTeamListBehavior() {
        Espresso.onView(withId(R.id.bottom_nav)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.menu_teams)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.spn_league)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.srl_teams)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.rv_teams)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.spn_league)).perform(ViewActions.click())
        Espresso.onData((CoreMatchers.instanceOf(League::class.java))).atPosition(6).perform(ViewActions.click())
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_teams))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(13))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_teams))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, ViewActions.click()))
        Espresso.onView(withId(R.id.vp_team_detail)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.nsv_team_overview))).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rl_team_overview))).check(ViewAssertions.matches(isDisplayed()))
        val matcher = allOf(withText("PLAYERS"), isDescendantOfA(withId(R.id.tablayout_team)))
        onView(matcher).perform(click())
        Thread.sleep(500)
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.nsv_team_player))).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_players))).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_players))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(16))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_players))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(14, ViewActions.click()))
        Espresso.onView(withId(R.id.sv_player_detail)).check(ViewAssertions.matches(isDisplayed()))
        pressBack()
        Espresso.onView(withId(R.id.add_to_favorites)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.add_to_favorites)).perform(ViewActions.click())
        Espresso.onView(withText("Added to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
        Espresso.onView(withId(R.id.bottom_nav))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.menu_fav)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.vp_favorite)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.vp_favorite)).perform(ViewActions.swipeLeft())
        Thread.sleep(500)
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_fav_team))).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.rv_fav_team))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Espresso.onView(withId(R.id.add_to_favorites)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.add_to_favorites)).perform(ViewActions.click())
        Espresso.onView(withText("Removed from favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
    }
}