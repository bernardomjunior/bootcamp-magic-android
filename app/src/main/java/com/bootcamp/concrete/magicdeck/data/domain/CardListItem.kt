package com.bootcamp.concrete.magicdeck.data.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bootcamp.concrete.magicdeck.BuildConfig.DATABASE_NAME
import kotlinx.android.parcel.Parcelize

sealed class CardListItem

@Entity(tableName = DATABASE_NAME)
@Parcelize
class Card(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "types")
    val types: List<String>,
    @ColumnInfo(name = "set")
    val set: String,
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?
) : Parcelable, CardListItem()

class CardListHeader(var text: String) : CardListItem()

class LoadingCards : CardListItem()