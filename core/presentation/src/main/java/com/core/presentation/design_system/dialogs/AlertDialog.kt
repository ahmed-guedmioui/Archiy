package com.core.presentation.design_system.dialogs

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object AlertDialog {
    fun show(type: AlertDialogType, message: String) {
        AlertDialogSender.eventChannel.trySend(AlertDialogData(type, message))
    }
}

object AlertDialogSender {
    internal val eventChannel = Channel<AlertDialogData>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val alertDialogEvent = eventChannel.receiveAsFlow()
}

enum class AlertDialogType {
    ERROR,
    WARNING,
    INFO
}

data class AlertDialogData(
    val type: AlertDialogType,
    val message: String
)