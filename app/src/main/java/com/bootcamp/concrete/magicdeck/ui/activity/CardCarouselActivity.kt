package com.bootcamp.concrete.magicdeck.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.extension.loadImage
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModel
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModelFactory
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModelState
import kotlinx.android.synthetic.main.activity_card_carousel.button_add_remove_favorite
import kotlinx.android.synthetic.main.activity_card_carousel.image_card_description_item

class CardCarouselActivity : AppCompatActivity(R.layout.activity_card_carousel) {

    private val ADD_STATE = 1
    private val REMOVE_STATE = 2
    private var state = ADD_STATE

    private val card: Card? by lazy {
        getCardExtra()
    }

    private val deckViewModel: DeckViewModel by viewModels {
        DeckViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpFields()
        observeViewModelResponse()
        observeViewModelState()
        card?.let(::checkCard)
    }

    private fun observeViewModelState() {
        deckViewModel.state.observe(this) {
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
        deckViewModel.response.observe(this) { response ->
            when (response) {
                is DeckViewModelState.Response.CardAdded -> {
                    Toast.makeText(
                        this@CardCarouselActivity,
                        R.string.card_added_success,
                        Toast.LENGTH_SHORT
                    ).show()
                    removeState()
                }
                is DeckViewModelState.Response.CardRemoved -> {
                    Toast.makeText(
                        this@CardCarouselActivity,
                        R.string.card_removed_success,
                        Toast.LENGTH_SHORT
                    ).show()
                    addState()
                }
                is DeckViewModelState.Response.Error -> {
                    Toast.makeText(
                        this@CardCarouselActivity,
                        response.messageId,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setUpFields() {
        image_card_description_item.loadImage(card?.imageUrl)
        button_add_remove_favorite.setOnClickListener {
            if (state == ADD_STATE) {
                addCardToDeck()
            } else {
                removeCardFromDeck()
            }
        }
    }

    private fun getCardExtra(): Card? {
        return intent?.extras?.getParcelable(CatalogActivity.CARD_EXTRA)
    }

    private fun addCardToDeck() {
        card?.let {
            deckViewModel.addCardToDeck(it)
        }
    }

    private fun removeCardFromDeck() {
        card?.let {
            deckViewModel.removeCardFromDeck(it)
        }
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

}