package com.viaplay.test.feature.details

import androidx.lifecycle.SavedStateHandle
import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.common.base.BaseViewModel
import com.viaplay.test.common.base.ViewState
import com.viaplay.test.common.ui.common.Routes.Companion.LINK
import com.viaplay.test.common.utils.cleanHref
import com.viaplay.test.domain.model.Link
import com.viaplay.test.domain.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(
    repository: BaseRepository<Section, String, String>,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<Section, SectionViewState, String, String, DetailsUiEvent>(
    repository,
    SectionViewState(base = ViewState(isLoading = true)),
    DetailsUiEvent::ShowWarning,
    savedStateHandle.get<Link>(LINK)?.id,
    savedStateHandle.get<Link>(LINK)?.href?.cleanHref()
) {
    fun onBackClick() {
        emitEvent(DetailsUiEvent.NavigateUp)
    }
}
