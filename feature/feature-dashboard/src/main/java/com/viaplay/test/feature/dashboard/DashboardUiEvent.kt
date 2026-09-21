package com.viaplay.test.feature.dashboard

import com.viaplay.test.common.base.UiEvent

sealed interface DashboardUiEvent : UiEvent {
    data class ShowWarning(override val message: String) : DashboardUiEvent, UiEvent.Warning
    data class Navigate(override val route: String) : DashboardUiEvent, UiEvent.Navigation
}
