package com.viaplay.test.feature.details

import com.viaplay.test.common.base.UiEvent

sealed interface DetailsUiEvent : UiEvent {
    data class ShowWarning(override val message: String) : DetailsUiEvent, UiEvent.Warning
    object NavigateUp : DetailsUiEvent, UiEvent.NavigateUp
}
