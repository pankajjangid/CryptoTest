package com.pankajjangid.cryptotest.viewmodel

import com.pankajjangid.cryptotest.R
import com.pankajjangid.cryptotest.TestDispatcherRule
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity
import com.pankajjangid.cryptotest.local.repository.CurrencyRepository
import com.pankajjangid.cryptotest.utils.Constant.CURRENCY_TYPE_CRYPTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    @get:Rule
    val instantExecutorRule = TestDispatcherRule()

    private val testDispatcher = StandardTestDispatcher()

    val repository: CurrencyRepository = mock()
    private lateinit var viewModel: CurrencyViewModel

    @Before
    fun setUp() {
        viewModel = CurrencyViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test insertToDatabase success`() = runTest {
        // Arrange
        viewModel.currencySetA = listOf(CurrencyEntity(1, "1", "Bitcoin", "BTC", CURRENCY_TYPE_CRYPTO))
        viewModel.currencySetB = listOf(CurrencyEntity(2, "2", "USD", "USD", CURRENCY_TYPE_CRYPTO))

        // Act
        viewModel.insertToDatabase()

        // Move the test dispatcher to ensure coroutines run
        advanceUntilIdle()

        // Assert
        assertEquals(R.string.insert_success, viewModel.message.first())
        verify(repository, times(1)).insertAll(anyList())
    }


    @Test
    fun `test loadCurrencyListByType success`() = runTest {
        // Arrange
        val currencies = listOf(CurrencyEntity(1, "1", "Bitcoin", "BTC", CURRENCY_TYPE_CRYPTO))
        `when`(repository.getCurrencyListByType(CURRENCY_TYPE_CRYPTO)).thenReturn(MutableStateFlow(currencies))

        // Act
        viewModel.loadCurrencyListByType(CURRENCY_TYPE_CRYPTO)

        // Move the test dispatcher to ensure coroutines run
        advanceUntilIdle()

        // Assert
        assertEquals(currencies, viewModel.getCurrency().first())
    }

    @Test
    fun `test loadCurrencyListByType failure`() = runTest {
        // Simulate failure in repository
        `when`(repository.getCurrencyListByType(CURRENCY_TYPE_CRYPTO)).thenReturn(MutableStateFlow(
            emptyList()
        ))

        // Act
        viewModel.loadCurrencyListByType(CURRENCY_TYPE_CRYPTO)

        // Move the test dispatcher to ensure coroutines run
        advanceUntilIdle()

        // Assert that the list remains empty after the failure
        assertEquals(emptyList<CurrencyEntity>(), viewModel.getCurrency().first())
    }


    @Test
    fun `test loadAllCurrencyFromDatabase success`() = runTest {
        // Arrange
        val currencies = listOf(CurrencyEntity(1, "1", "Bitcoin", "BTC", CURRENCY_TYPE_CRYPTO))
        `when`(repository.getAllCurrencyInfoFromDb()).thenReturn(MutableStateFlow(currencies))

        // Act
        viewModel.loadAllCurrencyFromDatabase()

        // Move the test dispatcher to ensure coroutines run
        advanceUntilIdle()

        // Assert
        assertEquals(currencies, viewModel.getCurrency().first())
    }

    @Test
    fun `test loadAllCurrencyFromDatabase failure`() = runTest {
        // Arrange
        `when`(repository.getAllCurrencyInfoFromDb()).thenReturn(MutableStateFlow(emptyList()))

        // Act
        viewModel.loadAllCurrencyFromDatabase()

        // Move the test dispatcher to ensure coroutines run
        advanceUntilIdle()

        // Assert
        assertEquals(emptyList<CurrencyEntity>(), viewModel.getCurrency().first())
    }
    @Test
    fun `test clearDB success`() = runTest {
        // Arrange
        `when`(repository.deleteAll()).thenReturn(0)
        `when`(repository.getAllCurrencyInfoFromDb()).thenReturn(MutableStateFlow(emptyList()))

        // Act
        viewModel.clearDB()

        // Move the test dispatcher to ensure coroutines run
        advanceUntilIdle()

        // Assert
        assertEquals(emptyList<CurrencyEntity>(), viewModel.getCurrency().first())
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset to the default dispatcher
    }
}
