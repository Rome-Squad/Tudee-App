package com.giraffe.tudeeapp.presentation.home


interface HomeActions {
    fun onTasksLinkClick(tabIndex: Int)
    fun onAddTaskClick()
    fun onTaskClick(taskId: Long)
    fun onEditTaskClick(taskId: Long?)
    fun showTaskAddedSuccess(message: String)
    fun showTaskEditedSuccess(message: String)
    fun dismissTaskDetails()
    fun dismissTaskEditor()
    fun dismissSnackBar()
}