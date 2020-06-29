package com.bootcamp.concrete.magicdeck.data.external

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bootcamp.concrete.magicdeck.di.networkModule
import com.bootcamp.concrete.magicdeck.mocks.successMockMapTypes
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

class TypeRepositoryTest : KoinTest {

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
    fun `givenSuccessResponse whenListing shouldReturnSuccessListOfTypes`() = runBlocking {
        //arrange
        val typeRepository = TypeRepository(retrofit, get())
        coEvery { retrofit.listTypes() } returns successMockMapTypes(javaClass.classLoader)
        val expected =
            ResultWrapper.Success(successMockMapTypes(javaClass.classLoader).values.toList()[0])

        //act
        val response = typeRepository.listTypes()

        //assert
        Assert.assertEquals(expected.value, (response as ResultWrapper.Success).value)
    }

    @Test
    fun `givenIOException whenListing shouldReturnNetWorkError`() = runBlocking {
        // arrange
        val typeRepository = TypeRepository(retrofit, get())
        coEvery { retrofit.listTypes() } throws IOException()
        val expected = ResultWrapper.NetworkError

        // act
        val response = typeRepository.listTypes()

        //assert
        Assert.assertEquals(expected, response)

    }

    @Test
    fun `givenHttpException whenListing shouldReturnGenericError`() = runBlocking {
        // arrange
        val typeRepository = TypeRepository(retrofit, get())
        coEvery { retrofit.listTypes() } throws HttpException(
            Response.error<Nothing>(
                404,
                "Not Found".toResponseBody(null)
            )
        )
        val expected = ResultWrapper.GenericError(404, "Not Found")

        // act
        val response = typeRepository.listTypes() as ResultWrapper.GenericError

        //assert
        Assert.assertEquals(expected.code, response.code)
        Assert.assertEquals(expected.error, response.error)
    }

    @Test
    fun `givenNonSpecificException whenListing shouldReturnEmptyGenericError`() = runBlocking {
        // arrange
        val typeRepository = TypeRepository(retrofit, get())
        coEvery { retrofit.listTypes() } throws Exception()
        val expected = ResultWrapper.GenericError(null, null)

        // act
        val response = typeRepository.listTypes() as ResultWrapper.GenericError

        //assert
        Assert.assertEquals(expected.code, response.code)
        Assert.assertEquals(expected.error, response.error)
    }

}