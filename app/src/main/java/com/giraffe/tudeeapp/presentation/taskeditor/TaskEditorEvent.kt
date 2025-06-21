package com.giraffe.tudeeapp.presentation.taskeditor

import com.giraffe.tudeeapp.domain.util.DomainError

sealed interface TaskEditorEvent {
    data class Error(val error: DomainError): TaskEditorEvent
    object TaskAddedSuccess: TaskEditorEvent
    object TaskEditedSuccess: TaskEditorEvent
    object DismissTaskEditor: TaskEditorEvent
}