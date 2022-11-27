package com.mahmoudgalal.myphoneinvoice

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.mahmoudgalal.myphoneinvoice.fragments.StartFragment
import android.text.TextUtils
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    private lateinit var copyRightText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
        if (BuildConfig.VERSION_CODE > 3) Utils.clearSharedPref(this)
        var userId = Utils.getUserID(this)
        if (userId == null) {
            userId = Utils.generateRandomUserId()
            Utils.saveUserID(userId, this)
        }
        val fragment = StartFragment()
        supportFragmentManager.addOnBackStackChangedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container, fragment,
                StartFragment::class.java.simpleName
            )
            .commit()
        copyRightText = findViewById(R.id.copy_right_txt)
        with(copyRightText){
            isSelected = true
            ellipsize = TextUtils.TruncateAt.MARQUEE
            text = "Copyright (c) 2021"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.privacy_action -> {
                val newIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(POLICY_URL)
                )
                // Create intent to show chooser
                val chooser = Intent.createChooser(newIntent, "Choose")
                // Verify the intent will resolve to at least one activity
                if (newIntent.resolveActivity(packageManager) != null) {
                    startActivity(chooser)
                }
                true
            }
            android.R.id.home -> {
                val fm = supportFragmentManager
                if (fm.backStackEntryCount > 0) {
                    fm.popBackStack()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackStackChanged() {
        val stackCount = supportFragmentManager.backStackEntryCount
        if (stackCount > 0) {
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }

        } else {
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(false)
                it.setDisplayShowHomeEnabled(false)
            }
        }
    }
    companion object{
        private const val POLICY_URL = "https://sites.google.com/view/mytelephone-invoices-app"
    }
}