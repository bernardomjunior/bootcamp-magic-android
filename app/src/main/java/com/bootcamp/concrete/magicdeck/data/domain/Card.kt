package com.bootcamp.concrete.magicdeck.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Card (
    val name: String,
    val types: List<String>,
    val set: String,
    val id: String,
    val imageUrl: String?
): Parcelable, CardListItem