package com.poittevin.francois.aurisquedevousplaire.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.poittevin.francois.aurisquedevousplaire.database.dao.CustomerDao
import com.poittevin.francois.aurisquedevousplaire.models.Customer

@Database(entities = [Customer::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArdvpDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao

    companion object {

        @Volatile
        private var instance: ArdvpDatabase? = null

        fun getInstance(context: Context): ArdvpDatabase =
            instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        )
                            .also { instance = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                ArdvpDatabase::class.java, "ArdvpDatabase.db"
            ).build()
    }
}