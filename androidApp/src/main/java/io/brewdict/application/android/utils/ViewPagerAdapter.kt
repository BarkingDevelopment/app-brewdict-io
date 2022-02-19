package io.brewdict.application.android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

public class ViewPagerAdapter(fm: FragmentManager, l: Lifecycle, private val fragments: Array<Fragment>): FragmentStateAdapter(fm, l){

    override fun getItemCount(): Int {
       return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}