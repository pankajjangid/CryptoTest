package com.pankajjangid.cryptotest.local.dao

import androidx.room.*
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyInfoEntity: CurrencyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyInfoEntity: List<CurrencyEntity>): LongArray

    @Update
    suspend fun update(currencyInfoEntity: CurrencyEntity): Int

    @Delete
    suspend fun delete(currencyInfoEntity: CurrencyEntity): Int

    @Query("DELETE FROM currency_entity")
    suspend fun deleteAllData():Int

    @Query("DELETE FROM currency_entity WHERE name = :name")
    suspend fun deleteCurrencyFromDb(name : String): Int

    @Query("SELECT * FROM currency_entity ORDER BY id asc")
     fun getAllCurrencyFromDb(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_entity")
     fun getCurrencyListFromDb(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_entity where type = :type")
     fun getCurrencyListByType(type : String): Flow<List<CurrencyEntity>>

    @Query("SELECT MAX(id) FROM currency_entity")
    suspend fun getMaxCurrencyId(): Int?

}