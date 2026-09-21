package com.viaplay.test.feature.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.common.utils.Async
import com.viaplay.test.domain.model.Link
import com.viaplay.test.domain.model.Section
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SectionViewModelTest {

    private val repository: BaseRepository<Section, String, String> = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val link = Link("1", "Title", "http://example.com/1")

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<Link>("link") } returns link
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has loading true`() = runTest {
        every { repository.getResult(any(), any(), any()) } returns flowOf(Async.Loading())
        
        val viewModel = SectionViewModel(repository, savedStateHandle)
        
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.base.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSuccess updates state with items`() = runTest {
        val section = Section("1", "Title", "Desc")
        every { repository.getResult(any(), any(), any()) } returns flowOf(Async.Success(section))

        val viewModel = SectionViewModel(repository, savedStateHandle)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(section, state.base.items)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onBackClick emits NavigateUp event`() = runTest {
        every { repository.getResult(any(), any(), any()) } returns flowOf(Async.Loading())
        val viewModel = SectionViewModel(repository, savedStateHandle)

        viewModel.uiEvent.test {
            viewModel.onBackClick()
            val event = awaitItem()
            assertTrue(event is DetailsUiEvent.NavigateUp)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
