package com.bootcamp.concrete.magicdeck.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.ui.adapter.CardsListAdapter
import com.bootcamp.concrete.magicdeck.ui.adapter.DividerItemDecoration
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModel
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModelFactory
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModelState
import kotlinx.android.synthetic.main.activity_catalog.cards_catalog

class CatalogActivity : AppCompatActivity() {

    private val cards = ArrayList<CardListItem>()
    private val catalogViewModel: CatalogViewModel by viewModels {
        CatalogViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        setUpList()

        catalogViewModel.getViewState().observe(this, {
            when (it){
                is CatalogViewModelState.NavigateToCarousel -> startCardCarouselActivity(it.card)
            }
        })


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
        val layoutManager =
            GridLayoutManager(this@CatalogActivity, resources.getInteger(R.integer.list_span_size))
        changeListItemSpanSize(layoutManager)
        cards_catalog.layoutManager = layoutManager
    }

    private fun changeListItemSpanSize(layoutManager: GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                cards_catalog.adapter?.let {
                    return it.getItemViewType(position)
                }
                return 1
            }
        }
    }

    private fun startCardCarouselActivity(card: Card) {
        val intent = Intent(this@CatalogActivity, CardCarouselActivity::class.java)
        intent.putExtra("card", card)
        startActivity(intent)
    }

}