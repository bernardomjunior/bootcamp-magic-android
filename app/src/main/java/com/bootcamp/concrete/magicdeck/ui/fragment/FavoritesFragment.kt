package com.bootcamp.concrete.magicdeck.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.ui.adapter.CardsListAdapter
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModel
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModelFactory
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModelState
import kotlinx.android.synthetic.main.fragment_favorites.favorites
import kotlinx.android.synthetic.main.fragment_favorites.favorites_title

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val deckViewModel: DeckViewModel by activityViewModels {
        DeckViewModelFactory()
    }

    private val favoriteList = ArrayList<CardListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFavorites()
        setUpFields()
        deckViewModel.getFavorites()
    }

    private fun observeFavorites() {
        deckViewModel.favorites.observe(viewLifecycleOwner) {
            when (it) {
                is DeckViewModelState.Deck.List -> {
                    listCards(it.items)
                }
                is DeckViewModelState.Deck.ListSize -> setTitle(it.size)
            }
        }
    }

    private fun setUpFields() {
        deckViewModel.getCardsAmount()
        context?.let { context ->
            favorites.adapter = CardsListAdapter(favoriteList, context) { card ->
                navigateToCarousel(card)
            }
        }
        val layoutManager =
            GridLayoutManager(activity, resources.getInteger(R.integer.list_span_size))
        changeListItemSpanSize(layoutManager)
        favorites.layoutManager = layoutManager
    }

    private fun changeListItemSpanSize(layoutManager: GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                favorites.adapter?.let { adapter ->
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

    private fun navigateToCarousel(card: Card) {
        val action = FavoritesFragmentDirections.actionNavigationFavoritesToNavigationCarousel(card)
        findNavController().navigate(action)
    }

    private fun listCards(list: List<CardListItem>) {
        deckViewModel.getCardsAmount()
        favoriteList.clear()
        favoriteList.addAll(list)
        favorites.adapter?.notifyDataSetChanged()
    }

    private fun setTitle(size: Int) {
        var title = resources.getString(R.string.favorites_title)
        title += " ($size)"
        favorites_title.text = title

    }

}