package com.hector.nailnewfinal.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.hector.mylibrary.ToolbarActivity
import com.hector.nailnewfinal.R
import com.hector.nailnewfinal.adapters.PagerAdapter
import com.hector.nailnewfinal.fragments.ChatFragment
import com.hector.nailnewfinal.fragments.InfoFragment
import com.hector.nailnewfinal.fragments.RatesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ToolbarActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var adapter: PagerAdapter

    private var prevBottomSelected: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarToLoad(toolbarView as Toolbar)
    }

    private fun getPagerAdapter(): PagerAdapter {
        adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(ChatFragment())
        adapter.addFragment(InfoFragment())
        adapter.addFragment(RatesFragment())
        return adapter
    }

    private fun setUpViewPager(adapter: PagerAdapter) {
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                //Selected Position in View Pager
                if (prevBottomSelected == null) {
                    //If is null is first time
                    bottomNavigation.menu.getItem(0).isChecked = false
                } else {
                    //Else any position and checked false, for when i move to other will inactive
                    prevBottomSelected!!.isChecked = false
                }
                //Here activate last menu, represented by prevBottomSelected, for when other interaction if is null or not this will inactivate
                bottomNavigation.menu.getItem(position).isChecked = true
                prevBottomSelected = bottomNavigation.menu.getItem(position)
            }

        })
    }

    private fun setUpBottomNavigationBar() {
//        bottomNavigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.
//            }
//        }
    }
}
