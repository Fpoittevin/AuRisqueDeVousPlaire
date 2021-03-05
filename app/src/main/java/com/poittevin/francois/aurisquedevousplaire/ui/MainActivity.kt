package com.poittevin.francois.aurisquedevousplaire.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.ActivityMainBinding
import com.poittevin.francois.aurisquedevousplaire.events.FailureEvent
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsFragment
import com.poittevin.francois.aurisquedevousplaire.ui.customerForm.CustomerFormFragment
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersAdapter
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersListFragment
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersListViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.message.MessageDialogFragment
import com.poittevin.francois.aurisquedevousplaire.utils.IsInternetAvailableLiveData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity(),
    CustomersAdapter.CustomerItemClickCallback,
    CustomerDetailsFragment.CustomerModificationFabListener,
    CustomerDetailsFragment.CustomerDeleteButtonListener,
    CustomerFormFragment.CustomerSaveListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val customerListFragment = CustomersListFragment.newInstance()
    private val customersListViewModel: CustomersListViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        configureToolbar()
        configureSearchView()

        displayCustomerListFragment()
    }

    private fun displayCustomerListFragment() {
        displayFragment(R.id.activity_main_first_frame_layout, customerListFragment)
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.activityMainToolbar)
    }

    private fun configureSearchView() {
        binding.activityMainSearchView.setOnQueryTextListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_toolbar_menu, menu)

        IsInternetAvailableLiveData(this).observe(this) {
            findViewById<ActionMenuItemView>(R.id.main_activity_toolbar_menu_message_button).apply {
                isEnabled = it
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_activity_toolbar_menu_creation_button -> displayFragment(
                R.id.activity_main_second_frame_layout,
                CustomerFormFragment.newInstance(null, this)
            )
            R.id.main_activity_toolbar_menu_message_button -> displayMessageDialogFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayMessageDialogFragment() {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        MessageDialogFragment.newInstance().show(fragmentTransaction, "message")
    }

    private fun displayFragment(layoutId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment)
            .commit()
    }

    private fun removeSecondFragment() {
        supportFragmentManager.findFragmentById(R.id.activity_main_second_frame_layout)?.let {
            supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }
    }

    override fun onCustomerItemClick(customerId: Int) {
        hideKeyboard()
        displayFragment(
            R.id.activity_main_second_frame_layout,
            CustomerDetailsFragment.newInstance(customerId, this, this)
        )
    }

    override fun onCustomerModificationFabClick(customerId: Int) {
        displayFragment(
            R.id.activity_main_second_frame_layout,
            CustomerFormFragment.newInstance(customerId, this)
        )
    }

    override fun onCustomerDeleteButtonClick() {
        removeSecondFragment()
    }

    override fun onCustomerSave(customerId: Int) {
        Log.e("Main Act onCustomerSave", customerId.toString())
        displayFragment(
            R.id.activity_main_second_frame_layout,
            CustomerDetailsFragment.newInstance(customerId, this, this)
        )
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val view = currentFocus ?: View(this)
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        customersListViewModel.searchLiveData.value = newText
        return true
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onFailureEvent(failureEvent: FailureEvent) {
        Log.e("ERROR", "onFailure: " + failureEvent.failureMessage)
        Toast.makeText(
            applicationContext,
            failureEvent.failureMessage,
            Toast.LENGTH_LONG
        ).show()
    }
}