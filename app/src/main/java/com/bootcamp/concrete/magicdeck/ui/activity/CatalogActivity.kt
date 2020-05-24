package com.bootcamp.concrete.magicdeck.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.ui.adapter.CardsListAdapter
import com.bootcamp.concrete.magicdeck.ui.adapter.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_catalog.cards_catalog

class CatalogActivity : AppCompatActivity() {

    private val cards = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        for(i in 1..100){
            cards.add(Card("a$i", listOf("2$i"), "123$i", "123213$i", "123$i"))
        }
    }

    private fun setUpList() {
        cards_catalog.addItemDecoration(
            DividerItemDecoration(
                resources.getInteger(R.integer.list_divider_size)
            )
        )
        cards_catalog.adapter = CardsListAdapter(cards, this@CatalogActivity) {
            startCardCarouselActivity(it)
        }
    }

    private fun startCardCarouselActivity(card: Card) {
        val intent = Intent(this@CatalogActivity, CardCarouselActivity::class.java)
        intent.putExtra("card", card)
        startActivity(intent)
    }

}