package com.viaplay.test.feature.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viaplay.test.common.base.Content
import com.viaplay.test.common.ui.common.ViaplaySwipeRefresh
import com.viaplay.test.domain.model.Link

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: LinksViewModel,
    navigateToDetail: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.dashboard))
                }
            )
        },
        content = { padding ->
            Content(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigate = navigateToDetail
            ) { state ->
                Column(modifier = Modifier.padding(padding)) {
                    ViaplaySwipeRefresh(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        state = state,
                        refresh = viewModel::refresh
                    ) {
                        VerticalCollection(state.base.items ?: emptyList(), viewModel::onLinkClick)
                    }
                }
            }
        })
}

@Composable
fun VerticalCollection(
    links: List<Link>,
    onItemClick: (Link) -> Unit
) {
    LazyColumn {
        items(
            items = links,
            itemContent = { link ->
                VerticalListItem(link = link, onItemClick = onItemClick)
            }
        )
    }
}

@Composable
private fun VerticalListItem(
    link: Link,
    onItemClick: (Link) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = { onItemClick(link) }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = link.title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
