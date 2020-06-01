package com.bootcamp.concrete.magicdeck.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.data.domain.LoadingCards
import com.bootcamp.concrete.magicdeck.ui.adapter.CardsListAdapter
import com.bootcamp.concrete.magicdeck.ui.decoration.DividerItemDecoration
import com.bootcamp.concrete.magicdeck.ui.listener.EndlessRecyclerViewScrollListener
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModel
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModelFactory
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModelState
import kotlinx.android.synthetic.main.activity_catalog.cards_catalog

class CatalogActivity : AppCompatActivity(R.layout.activity_catalog) {

    companion object{
        const val CARD_EXTRA = "card"
    }

    private val cards = ArrayList<CardListItem>()
    private val catalogViewModel: CatalogViewModel by viewModels {
        CatalogViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpList()
        observeViewModelState()
        observeViewModelLoading()
        catalogViewModel.getInitialCards()

    }

    private fun observeViewModelLoading() {
        catalogViewModel.getLoading().observe(this) {
            when (it) {
                is CatalogViewModelState.LoadingCards -> showProgressBar()
                is CatalogViewModelState.DoneLoading -> hideProgressBar()
            }
        }
    }

    private fun observeViewModelState() {
        catalogViewModel.getViewState().observe(this) { state ->
            when (state) {
                is CatalogViewModelState.NavigateToCarousel -> startCardCarouselActivity(state.card)
                is CatalogViewModelState.ListCards -> listCards(state.cardListItems)
                is CatalogViewModelState.Failure -> showErrorMessage()
                is CatalogViewModelState.Error -> showErrorMessage(state.stringId)
            }
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
        val layoutManager =
            GridLayoutManager(this@CatalogActivity, resources.getInteger(R.integer.list_span_size))
        changeListItemSpanSize(layoutManager)
        cards_catalog.layoutManager = layoutManager
        setListEndlessScroll(layoutManager)
    }

    private fun setListEndlessScroll(layoutManager: GridLayoutManager) {
        cards_catalog.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (cards.size > 1){
                    catalogViewModel.getCards()
                }
            }
        })
    }

    private fun changeListItemSpanSize(layoutManager: GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                cards_catalog.adapter?.let { adapter ->
                    return when (adapter.getItemViewType(position)){
                        CardsListAdapter.CARD -> 1
                        CardsListAdapter.CARD_LIST_HEADER -> 3
                        CardsListAdapter.LOADING -> 3
                        else -> 1
                    }
                }
                return 1
            }
        }
    }

    private fun startCardCarouselActivity(card: Card) {
        val intent = Intent(this@CatalogActivity, CardCarouselActivity::class.java)
        intent.putExtra(CARD_EXTRA, card)
        startActivity(intent)
    }

    private fun listCards(list: List<CardListItem>) {
        cards.addAll(list)
        cards_catalog.adapter?.notifyItemRangeInserted(
            cards.size - list.size,
            list.size
        )
    }

    private fun showErrorMessage(@StringRes stringId: Int) {
        Toast.makeText(this@CatalogActivity, resources.getString(stringId), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showErrorMessage() {
        Toast.makeText(
            this@CatalogActivity,
            resources.getString(R.string.request_failure),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showProgressBar(){
        if (!cards.isEmpty() && cards.last() is LoadingCards){
            return
        }
        cards.add(LoadingCards())
        cards_catalog.adapter?.notifyItemInserted(cards.size - 1)
    }

    private fun hideProgressBar(){
        cards.remove(cards.last())
        cards_catalog.adapter?.notifyItemRemoved(cards.size - 1)

    }

}