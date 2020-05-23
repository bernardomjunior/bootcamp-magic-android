package com.bootcamp.concrete.magicdeck.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.image_card_item

class CardsListAdapter(
        private val cardList: List<Card>,
        private val context: Context,
        private val listener: (card: Card) -> Unit
) : RecyclerView.Adapter<CardsListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(card: Card, listener: (card: Card) -> Unit) {
            Picasso.get()
                .load(card.imageUrl)
                .error(R.drawable.card_example)
                .into(itemView.image_card_item)
            itemView.image_card_item.setOnClickListener { listener(card) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.card_item, parent, false)
        )
    }

    override fun getItemCount() = cardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cardList[position], listener)
    }
}
