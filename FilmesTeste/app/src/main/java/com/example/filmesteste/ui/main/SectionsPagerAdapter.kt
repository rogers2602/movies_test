package com.example.filmesteste.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.filmesteste.fragments.FragFavorites
import com.example.filmesteste.fragments.FragCategories
import com.example.filmesteste.R

private val TAB_TITLES = arrayOf(
    R.string.tab_genres_1,
    R.string.tab_favorites_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragCategories()
            }
            1 ->{ FragFavorites()
            }
            else -> {return FragCategories()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}