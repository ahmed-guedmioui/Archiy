package com.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.domain.usecase.GetHomeItemsUseCase
import com.core.domain.util.result.DataError
import com.core.domain.util.result.onError
import com.core.domain.util.result.onSuccess
import com.core.presentation.util.error.UiText
import com.core.presentation.util.error.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeItemsUseCase: GetHomeItemsUseCase
) : ViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val event = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadItems()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = HomeState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.LoadItems -> loadItems()
            is HomeAction.OnItemClick -> {} // Navigate to item detail screen
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getHomeItemsUseCase()
                .onSuccess { items ->
                    _state.update {
                        it.copy(items = items.data ?: emptyList())
                    }
                }
                .onError { error ->
                    val message = when (error) {
                        is DataError -> error.toUiText()
                        else -> UiText.Resource(com.core.presentation.R.string.error_unknown)
                    }

                    eventChannel.send(HomeEvent.OnError(message))
                }

            _state.update { it.copy(isLoading = false) }
        }
    }
}