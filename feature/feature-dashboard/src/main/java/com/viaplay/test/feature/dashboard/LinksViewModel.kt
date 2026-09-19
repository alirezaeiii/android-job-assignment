package com.viaplay.test.feature.dashboard

import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.common.base.BaseViewModel
import com.viaplay.test.common.base.ViewState
import com.viaplay.test.domain.model.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinksViewModel @Inject constructor(
    repository: BaseRepository<List<Link>, Nothing, Nothing>
) : BaseViewModel<List<Link>, LinksViewState, Nothing, Nothing>(
    repository,
    LinksViewState(base = ViewState(isLoading = true))
)

