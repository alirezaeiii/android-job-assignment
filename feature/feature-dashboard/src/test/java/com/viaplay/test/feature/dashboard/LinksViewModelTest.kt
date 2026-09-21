package com.viaplay.test.feature.dashboard

import app.cash.turbine.test
import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.common.utils.Async
import com.viaplay.test.domain.model.Link
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import android.net.Uri
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
class LinksViewModelTest {

    private val repository: BaseRepository<List<Link>, Nothing, Nothing> = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Uri::class)
        every { Uri.encode(any()) } answers { firstArg() }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has loading true`() = runTest {
        every { repository.getResult(null, null, any()) } returns flowOf(Async.Loading())
        
        val viewModel = LinksViewModel(repository)
        
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.base.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSuccess updates state with items`() = runTest {
        val links = listOf(
            createLink("1", "Title 1"),
            createLink("2", "Title 2")
        )
        every { repository.getResult(null, null, true) } returns flowOf(Async.Success(links))

        val viewModel = LinksViewModel(repository)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(links, state.base.items)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLinkClick emits Navigate event`() = runTest {
        every { repository.getResult(null, null, true) } returns flowOf(Async.Loading())
        val viewModel = LinksViewModel(repository)
        val link = createLink("1", "Title 1")

        viewModel.uiEvent.test {
            viewModel.onLinkClick(link)
            val event = awaitItem()
            assertTrue(event is DashboardUiEvent.Navigate)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createLink(id: String, title: String) = Link(
        id = id,
        title = title,
        href = "http://example.com/$id"
    )
}
