package com.mahmoudgalal.myphoneinvoice.db

import android.content.Context
import androidx.room.Database
import com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse.Account.Invoice
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import com.mahmoudgalal.myphoneinvoice.db.model.dao.InvoicesDAO
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Invoice::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun invoiceDao(): InvoicesDAO
    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    val databaseCreated: LiveData<Boolean>
        get() = mIsDatabaseCreated

    companion object {
        private var sInstance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "Invoices-db"
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = buildDatabase(context.applicationContext)
                        sInstance!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return sInstance!!
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(
            appContext: Context
        ): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .build()
        }
    }
}