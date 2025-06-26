package com.giraffe.tudeeapp.presentation.screen.taskeditor

sealed interface TaskEditorEffect {
    data class Error(val error: Throwable): TaskEditorEffect
    object TaskAddedSuccess: TaskEditorEffect
    object TaskEditedSuccess: TaskEditorEffect
    object DismissTaskEditor: TaskEditorEffect
}