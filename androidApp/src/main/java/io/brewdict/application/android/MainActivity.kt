package io.brewdict.application.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.brewdict.application.android.R.layout.activity_landing
import io.brewdict.application.android.ui.login.LoginFragment
import io.brewdict.application.android.ui.register.RegisterFragment
import io.brewdict.application.android.utils.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    private var tabHeadings: Array<String> = arrayOf("Login", "Register")
    private var fragments: Array<Fragment> = arrayOf(LoginFragment(), RegisterFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_landing)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        var adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabHeadings[position]
        }.attach()
    }
}
