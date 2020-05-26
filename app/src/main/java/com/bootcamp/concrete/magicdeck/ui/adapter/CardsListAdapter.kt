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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.image_card_item
import kotlinx.android.synthetic.main.list_header.view.txt_list_header

class CardsListAdapter(
    private val cardList: List<CardListItem>,
    private val context: Context,
    private val listener: (card: Card) -> Unit
) : RecyclerView.Adapter<CardsListAdapter.ViewHolder>() {

    companion object {
        const val CARD = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listItem: CardListItem, listener: (card: Card) -> Unit) {
            when (listItem) {
                is Card -> initCard(listItem, listener)
                is CardListHeader -> initHeader(listItem)
            }

        }

        private fun initHeader(header: CardListHeader) {
            itemView.txt_list_header.text = header.text
        }

        private fun initCard(
            card: Card,
            listener: (card: Card) -> Unit
        ) {
            Picasso.get()
                .load(card.imageUrl)
                .error(R.drawable.card_example)
                .into(itemView.image_card_item)
            itemView.image_card_item.setOnClickListener { listener(card) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (cardList[position] is CardListHeader) 3 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == CARD) {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.card_item, parent, false
                )
            )
        }
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_header, parent, false
            )
        )

    }

    override fun getItemCount() = cardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cardList[position], listener)
    }
}
