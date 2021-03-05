package com.poittevin.francois.aurisquedevousplaire.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.poittevin.francois.aurisquedevousplaire.models.Customer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM Customer WHERE id= :id")
    fun getCustomerById(id: Int): LiveData<Customer>

    @Query("SELECT * FROM Customer WHERE id= :id")
    fun getCustomerForSync(id: Int):  Customer

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer): Long

    @Query("SELECT * FROM Customer ORDER BY lastName")
    fun getCustomersList(): LiveData<List<Customer>>

    @RawQuery(observedEntities = [Customer::class])
    fun getCustomersBySearch(query: SupportSQLiteQuery): LiveData<List<Customer>>

    @RawQuery(observedEntities = [Customer::class])
    fun getNumberOfMessages(query: SupportSQLiteQuery): LiveData<Int?>
}