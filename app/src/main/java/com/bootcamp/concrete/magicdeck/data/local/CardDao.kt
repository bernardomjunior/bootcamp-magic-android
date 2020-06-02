package com.bootcamp.concrete.magicdeck.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bootcamp.concrete.magicdeck.BuildConfig.DATABASE_NAME
import com.bootcamp.concrete.magicdeck.data.domain.Card

@Dao
interface CardDao {

    @Query("SELECT * FROM $DATABASE_NAME")
    fun getAll(): List<Card>

    @Insert
    fun addCard(card: Card)

    @Delete
    fun removeCard(card: Card)

}