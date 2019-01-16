package com.example.asus.aplikasifootballclub

import android.support.design.widget.TabLayout
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.TableLayout
import com.example.asus.aplikasifootballclub.main.MainActivity
import kotlinx.android.synthetic.main.fragment_teams.view.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BehaviorTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Next)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Next)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.nestedloopdetail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Next)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(14))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Next)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(14, ViewActions.click()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)
        Thread.sleep(1000)


        Espresso.onView(ViewMatchers.withId(R.id.viewpager_match)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.viewpager_match)).perform(ViewActions.swipeLeft())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Prev)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Prev)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Match_Prev)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, ViewActions.click()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.nestedloopdetail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click()) // add 6
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.viewpager_main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.viewpager_main)).perform(ViewActions.swipeLeft())
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Teams)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Teams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_Teams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Espresso.pressBack()
        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.viewpager_main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.viewpager_main)).perform(ViewActions.swipeLeft())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.recylermatch_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recylermatch_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))
        Thread.sleep(2000)
        Espresso.pressBack()
    }
}