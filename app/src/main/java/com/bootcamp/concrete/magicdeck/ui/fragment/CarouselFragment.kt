package com.bootcamp.concrete.magicdeck.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.extension.loadImage
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModel
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModelFactory
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModelState
import kotlinx.android.synthetic.main.fragment_carousel.button_add_remove_favorite
import kotlinx.android.synthetic.main.fragment_carousel.image_card_description_item

class CarouselFragment : Fragment(R.layout.fragment_carousel) {

    private val ADD_STATE = 1
    private val REMOVE_STATE = 2
    private var state = ADD_STATE

    lateinit var card: Card

    private val deckViewModel: DeckViewModel by activityViewModels {
        DeckViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: CarouselFragmentArgs by navArgs()
        card = args.card
        setUpFields()
        observeViewModelResponse()
        observeViewModelState()
        checkCard(card)
    }

    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        activity?.actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close_white_24)
        }
    }

    private fun observeViewModelState() {
        deckViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is DeckViewModelState.CardInDeck -> {
                    removeState()
                }
                is DeckViewModelState.CardNotInDeck -> {
                    addState()
                }
            }
        }
    }

    private fun observeViewModelResponse() {
        deckViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is DeckViewModelState.Response.CardAdded -> {
                    removeState()
                }
                is DeckViewModelState.Response.CardRemoved -> {
                    addState()
                }
                is DeckViewModelState.Response.Error -> {
                    Toast.makeText(
                        activity,
                        response.messageId,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setUpFields() {
        image_card_description_item.loadImage(card.imageUrl)
        button_add_remove_favorite.setOnClickListener {
            if (state == ADD_STATE) {
                addCardToDeck()
            } else {
                removeCardFromDeck()
            }
        }
        setUpToolbar()
    }


    private fun addCardToDeck() {
        deckViewModel.addCardToDeck(card)
    }

    private fun removeCardFromDeck() {
        deckViewModel.removeCardFromDeck(card)
    }

    private fun checkCard(card: Card) {
        deckViewModel.checkCard(card)
    }

    private fun addState() {
        button_add_remove_favorite.text = resources.getString(R.string.add_card_to_favorite)
        state = ADD_STATE
    }

    private fun removeState() {
        button_add_remove_favorite.text = resources.getString(R.string.remove_card_from_favorite)
        state = REMOVE_STATE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}