package com.example.asus.aplikasifootballclub.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.pagefavorite.FavoriteFragmentMain
import com.example.asus.aplikasifootballclub.pagematch.MatchFragmentMain
import com.example.asus.aplikasifootballclub.pageteams.TeamsFragmentMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        viewpager_main.adapter = MyPagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)
        val imageResId = intArrayOf(R.drawable.ic_teams, R.drawable.ic_teams, R.drawable.ic_favorites)
        tabs_main.getTabAt(0)?.setIcon(imageResId[0])
        tabs_main.getTabAt(1)?.setIcon(imageResId[1])
        tabs_main.getTabAt(2)?.setIcon(imageResId[2])
    }

    class MyPagerAdapter(fm: android.support.v4.app.FragmentManager): FragmentPagerAdapter(fm){
        private val pages = listOf(
                MatchFragmentMain(),
                TeamsFragmentMain(),
                FavoriteFragmentMain()
        )
        override fun getItem(position: Int): android.support.v4.app.Fragment? {
            return pages[position]
        }
        override fun getCount(): Int {
            return pages.size
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "Matches"
                1-> "Teams"
                else -> "Favorite"
            }
        }
    }

}