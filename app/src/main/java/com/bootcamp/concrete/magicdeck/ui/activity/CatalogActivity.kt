package com.bootcamp.concrete.magicdeck.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.ui.adapter.CardsListAdapter
import kotlinx.android.synthetic.main.activity_catalog.*

class CatalogActivity : AppCompatActivity() {

    val cards = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        for(i in 1..100){
            cards.add(Card("a$i", listOf("2$i"), "123$i", "123213$i", "123$i"))
        }
        setUpList()
    }

    fun setUpList(){
        cards_catalog.adapter = CardsListAdapter(cards, this@CatalogActivity){
            Toast.makeText(this@CatalogActivity, it.name, Toast.LENGTH_SHORT).show()
        }
    }

}