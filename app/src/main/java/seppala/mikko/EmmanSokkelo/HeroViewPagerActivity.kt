package seppala.mikko.EmmanSokkelo

import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentStatePagerAdapter



class HeroViewPagerActivity : FragmentActivity()
{

    private lateinit var pager : ViewPager
    private val pagerAdapter = HeroPagerAdapter(supportFragmentManager)
    private val fragments = arrayOf(HeroFragment(), HeroFragment(), HeroFragment())


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_view_pager)
        pager = findViewById(R.id.pager)
        pager.adapter = pagerAdapter
    }

    override fun onBackPressed()
    {
        if(pager.currentItem == 0) super.onBackPressed()
        else pager.currentItem = pager.currentItem - 1
    }


    private inner class HeroPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = fragments[position]
        override fun getCount(): Int = fragments.size

    }
}


