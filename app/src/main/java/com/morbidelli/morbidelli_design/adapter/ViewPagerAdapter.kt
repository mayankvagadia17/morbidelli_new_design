package com.morbidelli.morbidelli_design.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.morbidelli.morbidelli_design.fragment.ListFragment
import com.morbidelli.morbidelli_design.fragment.MapFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        MapFragment(),
        ListFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getMapFragment(): MapFragment = fragments[0] as MapFragment
    fun getListFragment(): ListFragment = fragments[1] as ListFragment
}
