package com.home.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.domain.usecase.GetHomeItemByIdUseCase
import com.core.domain.util.result.DataError
import com.core.domain.util.result.onError
import com.core.domain.util.result.onSuccess
import com.core.presentation.util.error.UiText
import com.core.presentation.util.error.toUiText
import com.home.presentation.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val getHomeItemByIdUseCase: GetHomeItemByIdUseCase
) : ViewModel() {

    private val eventChannel = Channel<ItemDetailEvent>()
    val event = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ItemDetailState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            initialValue = ItemDetailState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    fun onAction(action: ItemDetailAction) {
        when (action) {
            is ItemDetailAction.LoadItem -> loadItem(action.itemId)
            ItemDetailAction.OnBack -> {} // Navigates to previous screen
        }
    }

    private fun loadItem(itemId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getHomeItemByIdUseCase(itemId)
                .onSuccess { response ->
                    val item = response.data
                    if (item == null) {
                        val message = UiText.Resource(R.string.item_not_found)
                        eventChannel.send(ItemDetailEvent.OnError(message))
                    } else {
                        _state.update {
                            it.copy(item = item)
                        }
                    }
                }
                .onError { error ->
                    val message = when (error) {
                        is DataError -> error.toUiText()
                        else -> UiText.Resource(com.core.presentation.R.string.error_unknown)
                    }

                    eventChannel.send(ItemDetailEvent.OnError(message))
                }

            _state.update { it.copy(isLoading = false) }
        }
    }
}