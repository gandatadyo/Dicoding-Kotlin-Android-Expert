package com.example.asus.aplikasifootballclub.pagematch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import com.example.asus.aplikasifootballclub.R
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.support.v4.startActivity

class MatchFragmentMain : Fragment() {
    var viewPager:ViewPager?=null
    var tab_matchItem:TabLayout?=null
    var adapter:SampleAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_match, container, false)
        tab_matchItem = rootView.tab_match
        viewPager = rootView.viewpager_match
        adapter = SampleAdapter(childFragmentManager)
        viewPager?.adapter = adapter
        tab_matchItem?.setupWithViewPager(viewPager)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menuitemsearch, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.getItemId()
        if (id == R.id.btnSearchMenu) {
            startActivity<SearchMatchActivity>()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class SampleAdapter(fm: android.support.v4.app.FragmentManager): FragmentPagerAdapter(fm){
        private val pages = listOf(
                NextFragment(),
                PrevFragment()
        )
        override fun getItem(position: Int): android.support.v4.app.Fragment? {
            return pages[position]
        }
        override fun getCount(): Int {
            return pages.size
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "Next"
                1-> "Last"
                else -> ""
            }
        }
    }
}
