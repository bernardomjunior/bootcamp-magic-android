package com.bootcamp.concrete.magicdeck.data.external

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bootcamp.concrete.magicdeck.di.networkModule
import com.bootcamp.concrete.magicdeck.mocks.successMockMapCard
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CardRepositoryTest : KoinTest {

    @Before
    fun setUp() {
        startKoin {
            modules(networkModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    private val retrofit = mockk<ApiService>()
    private val pageSample = 1
    private val setSample = "10E"
    private val typeSample = "creature"

    @Test
    fun `givenApiSuccessResponse whenListing shouldReturnSuccessListOfCards`() = runBlocking {
        //arrange
        val cardRepository = CardRepository(retrofit, get())
        coEvery {
            retrofit.listCards(
                setSample,
                typeSample,
                pageSample
            )
        } returns successMockMapCard(javaClass.classLoader)
        val expected =
            ResultWrapper.Success(successMockMapCard(javaClass.classLoader).values.toList()[0])

        //act
        val response = cardRepository.listCards(setSample, typeSample, pageSample)

        //assert
        Assert.assertEquals(expected.value, (response as ResultWrapper.Success).value)
    }

    @Test
    fun `givenApiIOException whenListing shouldReturnNetWorkError`() = runBlocking {
        // arrange
        val cardRepository = CardRepository(retrofit, get())
        coEvery { retrofit.listCards(setSample, typeSample, pageSample) } throws IOException()
        val expected = ResultWrapper.NetworkError

        // act
        val response = cardRepository.listCards(setSample, typeSample, pageSample)

        //assert
        Assert.assertEquals(expected, response)

    }

    @Test
    fun `givenApiHttpException whenListing shouldReturnGenericError`() = runBlocking {
        // arrange
        val cardRepository = CardRepository(retrofit, get())
        coEvery { retrofit.listCards(setSample, typeSample, pageSample) } throws HttpException(
            Response.error<Nothing>(
                404,
                "Not Found".toResponseBody(null)
            )
        )
        val expected = ResultWrapper.GenericError(404, "Not Found")

        // act
        val response = cardRepository.listCards(
            setSample,
            typeSample,
            pageSample
        ) as ResultWrapper.GenericError

        //assert
        Assert.assertEquals(expected.code, response.code)
        Assert.assertEquals(expected.error, response.error)
    }

    @Test
    fun `givenApiNonSpecificException whenListing shouldReturnEmptyGenericError`() = runBlocking {
        // arrange
        val cardRepository = CardRepository(retrofit, get())
        coEvery { retrofit.listCards(setSample, typeSample, pageSample) } throws Exception()
        val expected = ResultWrapper.GenericError(null, null)

        // act
        val response = cardRepository.listCards(
            setSample,
            typeSample,
            pageSample
        ) as ResultWrapper.GenericError

        //assert
        Assert.assertEquals(expected.code, response.code)
        Assert.assertEquals(expected.error, response.error)
    }

}