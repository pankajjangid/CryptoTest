package com.pankajjangid.cryptotest.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_entity")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val nId: Int = 0,

    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "type")
    var type: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val nameList = name.split("\\s+".toRegex())
        val symbolList = symbol.split("\\s+".toRegex())
        val matchingCombinations = arrayListOf<String>(

            "${name.first()} ${symbol.first()}",
        )
        matchingCombinations.addAll(nameList)
        matchingCombinations.addAll(symbolList)

        return matchingCombinations.any {
            it.startsWith(query, ignoreCase = true)
        }
    }
}