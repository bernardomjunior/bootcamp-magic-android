package com.bootcamp.concrete.magicdeck.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
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
import kotlinx.android.synthetic.main.fragment_catalog.cards_catalog

class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val cards = ArrayList<CardListItem>()
    private val catalogViewModel: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        observeViewModelState()
        observeViewModelLoading()
        catalogViewModel.getInitialCards()

    }

    private fun observeViewModelLoading() {
        catalogViewModel.getLoading().observe(viewLifecycleOwner) {
            when (it) {
                is CatalogViewModelState.LoadingCards -> showProgressBar()
                is CatalogViewModelState.DoneLoading -> hideProgressBar()
            }
        }
    }

    private fun observeViewModelState() {
        catalogViewModel.getViewState().observe(viewLifecycleOwner) { state ->
            when (state) {
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
        activity?.let { activity ->
            cards_catalog.adapter = CardsListAdapter(cards, activity) { card ->
                navigateToCarousel(card)
            }

        }
        val layoutManager =
            GridLayoutManager(activity, resources.getInteger(R.integer.list_span_size))
        changeListItemSpanSize(layoutManager)
        cards_catalog.layoutManager = layoutManager
        setListEndlessScroll(layoutManager)
    }

    private fun setListEndlessScroll(layoutManager: GridLayoutManager) {
        cards_catalog.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (cards.size > 1) {
                    catalogViewModel.getCards()
                }
            }
        })
    }

    private fun changeListItemSpanSize(layoutManager: GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                cards_catalog.adapter?.let { adapter ->
                    return when (adapter.getItemViewType(position)) {
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

    private fun listCards(list: List<CardListItem>) {
        cards.addAll(list)
        cards_catalog.adapter?.notifyItemRangeInserted(
            cards.size - list.size,
            list.size
        )
    }

    private fun showErrorMessage(@StringRes stringId: Int) {
        Toast.makeText(
            activity,
            resources.getString(stringId),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorMessage() {
        Toast.makeText(
            activity,
            resources.getString(R.string.request_failure),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showProgressBar() {
        if (!cards.isEmpty() && cards.last() is LoadingCards) {
            return
        }
        cards.add(LoadingCards())
        cards_catalog.adapter?.notifyItemInserted(cards.size - 1)
    }

    private fun hideProgressBar() {
        cards.remove(cards.last())
        cards_catalog.adapter?.notifyItemRemoved(cards.size - 1)

    }

    private fun navigateToCarousel(card: Card) {
        val action = CatalogFragmentDirections.actionNavigationCatalogToNavigationCarousel(card)
        findNavController().navigate(action)

    }

}