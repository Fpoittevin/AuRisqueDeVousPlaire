package com.poittevin.francois.aurisquedevousplaire.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.work.*
import com.poittevin.francois.aurisquedevousplaire.api.CustomerHelper
import com.poittevin.francois.aurisquedevousplaire.database.dao.CustomerDao
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.utils.ContactChoice
import com.poittevin.francois.aurisquedevousplaire.workers.BaseApiWorker.Companion.CUSTOMER_ID_KEY
import com.poittevin.francois.aurisquedevousplaire.workers.DeleteCustomerWorker
import com.poittevin.francois.aurisquedevousplaire.workers.InsertCustomerWorker
import com.poittevin.francois.aurisquedevousplaire.workers.UpdateCustomerWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerRepository(private val customerDao: CustomerDao, private val context: Context) {

    private val customerHelper = CustomerHelper()
    private val networkConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun getCustomer(id: Int) = customerDao.getCustomerById(id)

    fun getCustomerForSync(id: Int) = customerDao.getCustomerForSync(id)

    fun getCustomersList() = MediatorLiveData<List<Customer>>().apply {
        addSource(customerDao.getCustomersList()) {
            if (it.isEmpty()) {
                addSource(customerHelper.getCustomersList()) { listFromDb ->
                    GlobalScope.launch(Dispatchers.IO) {
                        for (customer in listFromDb) {
                            customerDao.insertCustomer(customer)
                        }
                    }
                }
            } else {
                postValue(it)
            }
        }
    }

    suspend fun updateCustomer(customer: Customer) {
        customerDao.updateCustomer(customer)

        val data = Data.Builder().apply {
            customer.id?.let { putInt(CUSTOMER_ID_KEY, it) }
        }

        val networkWorkRequest = OneTimeWorkRequest
            .Builder(UpdateCustomerWorker::class.java)
            .setInputData(data.build())
            .setConstraints(networkConstraints)
            .build()

        WorkManager.getInstance(context).enqueue(networkWorkRequest)
    }

    fun updateCustomerFromServer(customer: Customer) {
        customerHelper.updateCustomer(customer)
    }

    suspend fun insertCustomer(customer: Customer) {
        customer.id = customerDao.insertCustomer(customer).toInt()

        val data = Data.Builder().apply {
            customer.id?.let { putInt(CUSTOMER_ID_KEY, it) }
        }

        val networkWorkRequest = OneTimeWorkRequest
            .Builder(InsertCustomerWorker::class.java)
            .setInputData(data.build())
            .setConstraints(networkConstraints)
            .build()

        WorkManager.getInstance(context).enqueue(networkWorkRequest)
    }

    fun insertCustomerFromServer(customer: Customer) {
        customerHelper.insertCustomer(customer)
    }

    suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)

        val data = Data.Builder().apply {
            customer.id?.let { putInt(CUSTOMER_ID_KEY, it) }
        }

        val networkWorkRequest = OneTimeWorkRequest
            .Builder(DeleteCustomerWorker::class.java)
            .setInputData(data.build())
            .setConstraints(networkConstraints)
            .build()

        WorkManager.getInstance(context).enqueue(networkWorkRequest)
    }

    fun deleteCustomerFromServer(customer: Customer) {
        customerHelper.deleteCustomer(customer)
    }

    fun searchCustomers(search: String): LiveData<List<Customer>> {
        val sqlStringRequest =
            "SELECT * FROM Customer WHERE lastName LIKE :search OR firstName LIKE :search OR emailAddress LIKE :search OR phoneNumber LIKE :search"
        val args = arrayOf("%$search%")
        val sqlRequest = SimpleSQLiteQuery(sqlStringRequest, args)

        return customerDao.getCustomersBySearch(sqlRequest)
    }

    fun getNumberOfMessages(
        withReduction: Boolean,
        minMaxNumberOfCards: Array<Int?>,
        contactChoice: ContactChoice?
    ): LiveData<Int?> {

        val stringBuilder = StringBuilder()
        val args = ArrayList<Any>()

        fun StringBuilder.addWhereOrAnd() {
            if (contains("WHERE")) {
                append(" AND ")
            } else {
                append(" WHERE ")
            }
        }

        stringBuilder.append("SELECT COUNT(*) FROM CUSTOMER").apply {

            contactChoice?.let {
                when (it) {
                    ContactChoice.SMS -> {
                        addWhereOrAnd()
                        append("phoneNumber IS NOT NULL AND contactChoice = ?")

                        args.add(2)
                    }
                    ContactChoice.EMAIL -> {
                        addWhereOrAnd()
                        append("emailAddress IS NOT NULL AND contactChoice = ?")

                        args.add(1)
                    }
                    ContactChoice.NOTHING -> {
                        addWhereOrAnd()
                        append("contactChoice = ?")

                        args.add(0)
                    }
                }
            }

            if (withReduction) {
                addWhereOrAnd()
                append("reductionNumber > 0")
            }

            minMaxNumberOfCards[0]?.let {
                addWhereOrAnd()
                append("cardsNumber >= ?")
                args.add(it)
            }

            minMaxNumberOfCards[1]?.let {
                addWhereOrAnd()
                append("cardsNumber <= ?")
                args.add(it)
            }
        }

        val sqlRequest = SimpleSQLiteQuery(stringBuilder.toString(), args.toArray())

        return customerDao.getNumberOfMessages(sqlRequest)
    }
}