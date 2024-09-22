package com.pankajjangid.cryptotest.local.repository

import com.pankajjangid.cryptotest.local.db.CurrencyDatabase
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity

class CurrencyRepository(private val currencyDatabase: CurrencyDatabase) {
    suspend fun insert(currencyEntity: CurrencyEntity) =
        currencyDatabase.currencyDao().insert(currencyEntity)

    suspend fun insertAll(currencyEntity: List<CurrencyEntity>) =
        currencyDatabase.currencyDao().insertAll(currencyEntity)

    suspend fun update(currencyEntity: CurrencyEntity) =
        currencyDatabase.currencyDao().update(currencyEntity)

    suspend fun deleteAll() = currencyDatabase.currencyDao().deleteAllData()

    suspend fun delete(currencyEntity: CurrencyEntity) =
        currencyDatabase.currencyDao().delete(currencyEntity)

    suspend fun getMaxCurrencyId() = currencyDatabase.currencyDao().getMaxCurrencyId()

    fun getCurrencyListByType(type:String) = currencyDatabase.currencyDao().getCurrencyListByType(type)

    fun getAllCurrencyInfoFromDb() = currencyDatabase.currencyDao().getAllCurrencyFromDb()

    fun getCurrencyListFromDb() = currencyDatabase.currencyDao().getCurrencyListFromDb()
}