package com.ericjohnson.footballapps


import android.support.test.espresso.Espresso.*
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerViewAccessibilityDelegate
import com.ericjohnson.footballapps.R.id.*
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.utils.EspressoIdlingResource
import com.ericjohnson.footballapps.view.mainactivity.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by johnson on 10/14/18.
 */

@RunWith(AndroidJUnit4::class)
class PastMatchTest {

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
    fun testPastMatchesBehavior() {
        onView(ViewMatchers.withId(bottom_nav)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(rl_matches_container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(vp_match_schedule)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(spn_league)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(allOf(isDisplayed(), ViewMatchers.withId(srl_schedule))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(allOf(isDisplayed(), ViewMatchers.withId(rv_schedule_list))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(spn_league)).perform(click())
        onData((instanceOf(League::class.java))).atPosition(6).perform(click())
//        onView(allOf(isDisplayed(),ViewMatchers.withId(rv_schedule_list))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
//        onView(allOf(isDisplayed(),ViewMatchers.withId(rv_schedule_list))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        onView(ViewMatchers.withId(add_to_favorites)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(add_to_favorites)).perform(click())
        onView(ViewMatchers.withText("Added to favorite"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()


        onView(ViewMatchers.withId(bottom_nav))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(menu_fav)).perform(click())
        onView(ViewMatchers.withId(menu_matches)).perform(click())

        onView(ViewMatchers.withId(rl_matches_container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(vp_match_schedule)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(spn_league)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(allOf(isDisplayed(), ViewMatchers.withId(srl_schedule))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(allOf(isDisplayed(), ViewMatchers.withId(rv_schedule_list))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(spn_league)).perform(click())
        onData((instanceOf(League::class.java))).atPosition(6).perform(click())
        onView(allOf(isDisplayed(),ViewMatchers.withId(rv_schedule_list))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(allOf(isDisplayed(),ViewMatchers.withId(rv_schedule_list))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
                onView(ViewMatchers.withId(add_to_favorites)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(add_to_favorites)).perform(click())
        onView(ViewMatchers.withText("Removed from favorite"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()

        onView(ViewMatchers.withId(bottom_nav))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(menu_fav)).perform(click())
    }
}
