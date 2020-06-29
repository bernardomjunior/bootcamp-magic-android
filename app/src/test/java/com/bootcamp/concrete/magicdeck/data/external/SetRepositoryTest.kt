package com.bootcamp.concrete.magicdeck.data.external

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bootcamp.concrete.magicdeck.di.networkModule
import com.bootcamp.concrete.magicdeck.mocks.successMockMapSet
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
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

class SetRepositoryTest : KoinTest {

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

    @Test
    fun `givenApiSuccessResponse whenListing shouldReturnSuccessListOfSets`() = runBlocking {
        // arrange
        val setRepository = SetRepository(retrofit, get())
        coEvery { retrofit.listSets() } returns successMockMapSet(javaClass.classLoader)
        val expected =
            ResultWrapper.Success(successMockMapSet(javaClass.classLoader).values.toList()[0])

        //act
        val response = setRepository.listSets()

        //assert
        assertEquals(expected.value, (response as ResultWrapper.Success).value)
    }

    @Test
    fun `givenApiIOException whenListing shouldReturnNetWorkError`() = runBlocking {
        // arrange
        val setRepository = SetRepository(retrofit, get())
        coEvery { retrofit.listSets() } throws IOException()
        val expected = ResultWrapper.NetworkError

        // act
        val response = setRepository.listSets()

        //assert
        assertEquals(expected, response)

    }

    @Test
    fun `givenApiHttpException whenListing shouldReturnGenericError`() = runBlocking {
        // arrange
        val setRepository = SetRepository(retrofit, get())
        coEvery { retrofit.listSets() } throws HttpException(
            Response.error<Nothing>(
                404,
                "Not Found".toResponseBody(null)
            )
        )
        val expected = ResultWrapper.GenericError(404, "Not Found")

        // act
        val response = setRepository.listSets() as ResultWrapper.GenericError

        //assert
        assertEquals(expected.code, response.code)
        assertEquals(expected.error, response.error)
    }

    @Test
    fun `givenApiNonSpecificException whenListing shouldReturnEmptyGenericError`() = runBlocking {
        // arrange
        val setRepository = SetRepository(retrofit, get())
        coEvery { retrofit.listSets() } throws Exception()
        val expected = ResultWrapper.GenericError(null, null)

        // act
        val response = setRepository.listSets() as ResultWrapper.GenericError

        //assert
        assertEquals(expected.code, response.code)
        assertEquals(expected.error, response.error)
    }

}