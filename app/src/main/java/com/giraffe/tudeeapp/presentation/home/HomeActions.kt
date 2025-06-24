package com.giraffe.tudeeapp.presentation.home


interface HomeActions {
    fun onTasksLinkClick(tabIndex: Int)
    fun onAddTaskClick()
    fun onTaskClick(taskId: Long)
    fun onEditTaskClick(taskId: Long?)
    fun onDismissTaskDetailsRequest()
    fun onDismissTaskEditorRequest()
    fun onToggleTheme()
}