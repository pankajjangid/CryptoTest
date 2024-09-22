package com.pankajjangid.cryptotest.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pankajjangid.cryptotest.utils.Constant
import com.pankajjangid.cryptotest.local.dao.CurrencyDao
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {

        fun provideDatabase(context: Context) =
            Room.databaseBuilder(context, CurrencyDatabase::class.java, Constant.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

    }
}
