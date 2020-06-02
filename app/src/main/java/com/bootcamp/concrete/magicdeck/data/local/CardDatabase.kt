package com.bootcamp.concrete.magicdeck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bootcamp.concrete.magicdeck.data.domain.Card

@Database(entities = [Card::class], version = 1)
abstract class CardDatabase : RoomDatabase(){
    abstract fun cardDao(): CardDao
}