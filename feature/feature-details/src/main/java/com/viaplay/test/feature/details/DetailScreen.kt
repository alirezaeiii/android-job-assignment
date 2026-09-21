package com.viaplay.test.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viaplay.test.common.base.Content
import com.viaplay.test.common.ui.common.ViaplaySwipeRefresh
import com.viaplay.test.common.utils.cleanHref
import com.viaplay.test.domain.model.Link

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: SectionViewModel,
    link: Link?,
    navigateUp: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.section))
                },
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        content = { padding ->
            Content(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                refresh = { viewModel.refresh(link?.id, link?.href?.cleanHref()) },
                onNavigateUp = navigateUp
            ) { state ->
                Column(modifier = Modifier.padding(padding)) {
                    ViaplaySwipeRefresh(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        state = state,
                        refresh = { viewModel.refresh(link?.id, link?.href?.cleanHref()) }
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp)
                        ) {
                            state.base.items?.let { section ->
                                DetailTitleText(resourceId = R.string.title)
                                Spacer(Modifier.height(8.dp))
                                DetailDescriptionText(text = section.title)
                                Spacer(Modifier.height(16.dp))
                                DetailTitleText(resourceId = R.string.description)
                                Spacer(Modifier.height(8.dp))
                                DetailDescriptionText(text = section.description)
                            }
                        }
                    }
                }
            }
        })
}

@Composable
fun DetailTitleText(resourceId: Int) {
    Text(
        text = stringResource(id = resourceId),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}

@Composable
fun DetailDescriptionText(text: String) {
    Text(
        text = text,
        fontSize = 16.sp
    )
}