package seppala.mikko.EmmanSokkelo

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentStatePagerAdapter
import android.widget.Button


class HeroViewPagerActivity : FragmentActivity()
{

    private lateinit var pager : ViewPager
    private lateinit var acceptButton : Button
    private val pagerAdapter = HeroPagerAdapter(supportFragmentManager)

    private var fragments = emptyList<HeroFragment>()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_view_pager)

        fragments += createFragment("Prinsessa", R.drawable.hero_500px)
        fragments += createFragment("Tonttu", R.drawable.tonttu_500px)

        pager = findViewById(R.id.pager)
        pager.adapter = pagerAdapter

        acceptButton = findViewById(R.id.hero_select_button)
        acceptButton.setOnClickListener({_ ->  onSelectButtonClick()})
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

    private fun createFragment(name: String, imageId: Int) : HeroFragment
    {
        val fragment = HeroFragment()

        val b = Bundle()
        b.putString("name", name)
        b.putInt("imageId", imageId)
        fragment.arguments = b

        return fragment
    }

    private fun onSelectButtonClick()
    {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("heroDrawableId", fragments[pager.currentItem].imageId)
        startActivity(i)
    }
}


