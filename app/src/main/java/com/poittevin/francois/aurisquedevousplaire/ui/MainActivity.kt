package com.poittevin.francois.aurisquedevousplaire.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.ActivityMainBinding
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsFragment
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersAdapter
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersListFragment

class MainActivity : AppCompatActivity(), CustomersAdapter.CustomerItemClickCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        configureToolbar()

        displayFragment(R.id.activity_main_first_frame_layout, CustomersListFragment.newInstance())
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.activityMainToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun displayFragment(layoutId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment)
            .commit()
    }

    override fun onCustomerItemClick(customerId: Int) {
        Log.e("id", customerId.toString())
        displayFragment(R.id.activity_main_second_frame_layout, CustomerDetailsFragment.newInstance(customerId))
    }
}