package com.bootcamp.concrete.magicdeck

import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.di.networkModule
import com.bootcamp.concrete.magicdeck.di.repositoryModule
import com.bootcamp.concrete.magicdeck.di.viewModelModule
import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class CardListingUnitTest: KoinTest {

    @Before
    fun setUp(){
        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }

    @After
    fun tearDown(){
        stopKoin()
    }

    @Test
    fun `givenAAA_whenBBB_shouldCCC`(){
        //arrange
        val catalogViewModel: CatalogViewModel by inject()
        var cardList = arrayListOf<Card>()
        for (i in 1..100){
            cardList.add(Card("", listOf("Creature"), "", "1$i", ""))
        }

        //act

        //assert
        assert(true)
    }

}