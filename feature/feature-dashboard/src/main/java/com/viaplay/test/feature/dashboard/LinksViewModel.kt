package com.viaplay.test.feature.dashboard

import android.net.Uri
import com.google.gson.Gson
import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.common.base.BaseViewModel
import com.viaplay.test.common.base.ViewState
import com.viaplay.test.common.ui.common.Routes
import com.viaplay.test.common.ui.common.Routes.Companion.LINK
import com.viaplay.test.domain.model.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinksViewModel @Inject constructor(
    repository: BaseRepository<List<Link>, Nothing, Nothing>
) : BaseViewModel<List<Link>, LinksViewState, Nothing, Nothing, DashboardUiEvent>(
    repository,
    LinksViewState(base = ViewState(isLoading = true))
) {
    override fun createWarningEvent(message: String): DashboardUiEvent {
        return DashboardUiEvent.ShowWarning(message)
    }

    fun onLinkClick(link: Link) {
        val json = Uri.encode(Gson().toJson(link))
        val route = Routes.Details.title.replace("{${LINK}}", json)
        emitEvent(DashboardUiEvent.Navigate(route))
    }
}
