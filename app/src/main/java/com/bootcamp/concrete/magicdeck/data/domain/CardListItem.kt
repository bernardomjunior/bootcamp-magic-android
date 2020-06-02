package com.bootcamp.concrete.magicdeck.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CardListItem

@Parcelize
class Card(
    val name: String,
    val types: List<String>,
    val set: String,
    val id: String,
    val imageUrl: String?
) : Parcelable, CardListItem()

class CardListHeader(var text: String) : CardListItem()

class LoadingCards() : CardListItem()