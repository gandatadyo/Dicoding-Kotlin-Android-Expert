package com.example.asus.aplikasifootballclub.pagefavorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.asus.aplikasifootballclub.R
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragmentMain : Fragment() {
    var viewPager: ViewPager?=null
    var tab_matchItem: TabLayout?=null
    var adapter:SampleAdapterFav?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        tab_matchItem = rootView.tab_favorite
        viewPager = rootView.viewpager_favorite
        adapter = SampleAdapterFav(childFragmentManager)
        viewPager?.adapter = adapter
        tab_matchItem?.setupWithViewPager(viewPager)
        return rootView
    }

    class SampleAdapterFav(fm: android.support.v4.app.FragmentManager): FragmentPagerAdapter(fm){
        private val pagesfav = listOf(
                FavoriteMatch(),
                FavoriteTeams()
        )
        override fun getItem(position: Int): android.support.v4.app.Fragment? {
            return pagesfav[position]
        }
        override fun getCount(): Int {
            return pagesfav.size
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "Match"
                1-> "Teams"
                else -> ""
            }
        }
    }

}