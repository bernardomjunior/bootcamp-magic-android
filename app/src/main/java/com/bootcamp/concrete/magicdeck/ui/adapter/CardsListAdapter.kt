package com.bootcamp.concrete.magicdeck.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListHeader
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.data.domain.LoadingCards
import com.bootcamp.concrete.magicdeck.extension.loadImage
import kotlinx.android.synthetic.main.card_item.view.image_card_item
import kotlinx.android.synthetic.main.card_item.view.text_card_item_name
import kotlinx.android.synthetic.main.list_header.view.txt_list_header

class CardsListAdapter(
    private val cardList: List<CardListItem>,
    private val context: Context,
    private val listener: (card: Card) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PLACE_HOLDER_CARD = 0
        const val CARD = 1
        const val CARD_LIST_HEADER = 2
        const val LOADING = 3
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class PlaceHolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            card: Card,
            listener: (card: Card) -> Unit
        ) {
            itemView.text_card_item_name.visibility = View.VISIBLE
            itemView.text_card_item_name.text = card.name
            itemView.image_card_item.setImageResource(R.drawable.blank_card)
            itemView.setOnClickListener { listener(card) }
        }
    }

    class HeaderPlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(header: CardListHeader) {
            itemView.txt_list_header.text = header.text
        }
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            card: Card,
            listener: (card: Card) -> Unit
        ) {
            itemView.text_card_item_name.visibility = View.GONE
            itemView.image_card_item.loadImage(card.imageUrl)
            itemView.setOnClickListener { listener(card) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (cardList[position]) {
            is Card -> getCardType(cardList[position] as Card)
            is CardListHeader -> CARD_LIST_HEADER
            is LoadingCards -> LOADING
        }
    }

    private fun getCardType(card: Card): Int {
        if (card.imageUrl == null) {
            return PLACE_HOLDER_CARD
        }
        return CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PLACE_HOLDER_CARD -> PlaceHolderViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.card_item, parent, false
                )
            )
            LOADING -> LoadingViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.list_loading, parent, false
                )
            )
            CARD_LIST_HEADER -> HeaderPlaceHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.list_header, parent, false
                )
            )
            else -> CardViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.card_item, parent, false
                )
            )
        }
    }

    override fun getItemCount() = cardList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CARD -> (holder as CardViewHolder).bind(cardList[position] as Card, listener)
            PLACE_HOLDER_CARD -> (holder as PlaceHolderViewHolder).bind(
                cardList[position] as Card,
                listener
            )
            CARD_LIST_HEADER -> (holder as HeaderPlaceHolder).bind(cardList[position] as CardListHeader)
        }
    }
}
