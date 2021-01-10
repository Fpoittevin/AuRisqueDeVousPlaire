package com.poittevin.francois.aurisquedevousplaire.ui

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.ActivityMainBinding
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsFragment
import com.poittevin.francois.aurisquedevousplaire.ui.customerForm.CustomerFormFragment
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersAdapter
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersListFragment

class MainActivity : AppCompatActivity(),
    CustomersAdapter.CustomerItemClickCallback,
    CustomerDetailsFragment.CustomerModificationFabListener,
    CustomerFormFragment.CustomerSaveListener {

    private lateinit var binding: ActivityMainBinding
    private val customerListFragment = CustomersListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        configureToolbar()

        displayFragment(R.id.activity_main_first_frame_layout, customerListFragment)
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.activityMainToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_activity_toolbar_menu_creation_button -> displayFragment(
                R.id.activity_main_second_frame_layout,
                CustomerFormFragment.newInstance(null, this)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayFragment(layoutId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment)
            .commit()
    }

    override fun onCustomerItemClick(customerId: Int) {
        displayFragment(
            R.id.activity_main_second_frame_layout,
            CustomerDetailsFragment.newInstance(customerId, this)
        )
    }

    override fun onCustomerModificationFabClick(customerId: Int) {
        displayFragment(
            R.id.activity_main_second_frame_layout,
            CustomerFormFragment.newInstance(customerId, this)
        )
    }

    override fun onCustomerSave(customerId: Int) {
        displayFragment(
            R.id.activity_main_second_frame_layout,
            CustomerDetailsFragment.newInstance(customerId, this)
        )
        hideKeyboard()
        customerListFragment.updateCustomersList()
    }

    private fun hideKeyboard() {
        val view = currentFocus ?: View(this)
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}