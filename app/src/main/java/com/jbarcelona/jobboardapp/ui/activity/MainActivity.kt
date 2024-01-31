package com.jbarcelona.jobboardapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.ui.fragment.FilterJobFragment
import com.jbarcelona.jobboardapp.ui.fragment.JobListingFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Filter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(JobListingFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_content, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_filter) {
            loadFragment(FilterJobFragment())
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}