# 🌤 Tudee – Jetpack Compose Task Management App

As a Team, our task is to develop a personal task management app for Android.

---
## Screen shots
<table style="width: 100%; border-collapse: collapse;"><tbody><tr><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;">Onboarding</th><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;">Home Dark</th><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;">Home Light</th><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;">Category</th></tr><tr><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Onboarding" src="https://github.com/user-attachments/assets/dfe06fab-0189-4235-acac-64682c509eea"></td><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Home Dark" src="https://github.com/user-attachments/assets/082fff24-dc31-4719-a09e-14080d04b5a6"></td><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Home Light" src="https://github.com/user-attachments/assets/58af9405-47c0-4c95-b633-6ccf26b46964"></td><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Category" src="https://github.com/user-attachments/assets/f22e1e9b-4d99-4604-8f53-0ef6861fd835"></td></tr><tr><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;">Task</th><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;">Categories</th><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"></th><th style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"></th></tr><tr><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Task" src="https://github.com/user-attachments/assets/89c9da94-7a79-4454-ba25-96afae49ec4f"></td><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Categories" src="https://github.com/user-attachments/assets/f0d185f9-c42c-4282-8eac-3be5c67a091d"></td><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"></td><td style="width: 25%; text-align: center; border: 1px solid #ccc; padding: 8px;"></td></tr></tbody></table>


## 🧠 Key Concepts

This app is a practical demonstration of:

- Jetpack Compose UI
- Clean MVI Architecture
- Koin for Dependency Injection
- Coil
- Single Responsibility & SOLID Principles

---

## 📱 Features

- onboarding screen to appear only the first time I launch the app.
- home screen displaying statistics about today’s tasks.
- create a new task with a title, description, priority, and category.
- view the full details of any task.
- view all tasks based on a selected date.
- delete any task.
- change a task’s status from "To Do" to "In Progress," and from "In Progress" to "Done."
- see a list of predefined categories.
- add a new category, including selecting an image from my device.
- edit or delete any category that I created.
- switch between light and dark mode.
- the app to follow the device’s language settings and support both English and Arabic (no separate settings screen is required).

---

## 🛠️ Tech Stack

| Tech                    | Usage                         |
|-------------------------|-------------------------------|
| **Kotlin**              | Programming Language          |
| **Jetpack Compose**     | Declarative UI Framework      |
| **MVI**                 | Architecture Pattern          |
| **Koin**                | Dependency Injection          |

---
## 🧩 Architecture
<pre>
├── data
│   ├── database
│   │   ├── CategoryDao.kt
│   │   ├── TaskDao.kt
│   │   └── TudeeDatabase.kt
│   ├── mapper
│   │   ├── CategoryMapper.kt
│   │   └── TaskMapper.kt
│   ├── model
│   │   ├── CategoryEntity.kt
│   │   └── TaskEntity.kt
│   ├── service
│   │   ├── CategoryServiceImp.kt
│   │   ├── MainServiceImpl.kt
│   │   ├── SplashService.kt
│   │   └── TasksServiceImp.kt
│   └── util
│       ├── Constants.kt
│       └── safeCall.kt
├── design_system
│   ├── color
│   │   ├── darkThemeColor.kt
│   │   ├── lightThemeColor.kt
│   │   └── TudeeColors.kt
│   ├── component
│   │   ├── AlertBottomSheet.kt
│   │   ├── AppBar.kt
│   │   ├── button_type
│   │   │   ├── FabButton.kt
│   │   │   ├── NegativeButton.kt
│   │   │   ├── NegativeTextButton.kt
│   │   │   ├── PrimaryButton.kt
│   │   │   ├── SecondaryButton.kt
│   │   │   └── TextButton.kt
│   │   ├── CategoryBottomSheet.kt
│   │   ├── CategoryItem.kt
│   │   ├── DatePickerDialog.kt
│   │   ├── DayCard.kt
│   │   ├── DefaultTextField.kt
│   │   ├── HeaderContent.kt
│   │   ├── LabelIconBox.kt
│   │   ├── NavBar.kt
│   │   ├── NoTasksSection.kt
│   │   ├── ParagraphTextField.kt
│   │   ├── Priority.kt
│   │   ├── Slider.kt
│   │   ├── TabsBar.kt
│   │   ├── TaskCard.kt
│   │   ├── ThemeSwitch.kt
│   │   ├── TudeeSnackBar.kt
│   │   └── TudeeTopBar.kt
│   ├── resources
│   │   └── TudeeResources.kt
│   ├── text_style
│   │   ├── defaultTextStyle.kt
│   │   ├── Font.kt
│   │   └── TudeeTextStyle.kt
│   └── theme
│       ├── Theme.kt
│       └── TudeeTheme.kt
├── di
│   ├── appModule.kt
│   └── dataModule.kt
├── domain
│   ├── model
│   │   ├── Category.kt
│   │   └── task
│   │       ├── Task.kt
│   │       ├── TaskPriority.kt
│   │       └── TaskStatus.kt
│   ├── service
│   │   ├── CategoriesService.kt
│   │   ├── MainService.kt
│   │   ├── SplashService.kt
│   │   └── TasksService.kt
│   └── util
│       ├── DomainError.kt
│       └── Result.kt
├── MainActivity.kt
├── presentation
│   ├── categories
│   │   ├── CategoriesRoute.kt
│   │   ├── CategoriesScreenActions.kt
│   │   ├── CategoriesScreenEvents.kt
│   │   ├── CategoriesScreen.kt
│   │   ├── CategoriesScreenState.kt
│   │   └── CategoryViewModel.kt
│   ├── home
│   │   ├── composable
│   │   │   ├── CardOverView.kt
│   │   │   ├── NoTask.kt
│   │   │   ├── OverViewSection.kt
│   │   │   ├── SliderStatus.kt
│   │   │   ├── TaskSection.kt
│   │   │   ├── TitleOverView.kt
│   │   │   └── TopSlider.kt
│   │   ├── HomeActions.kt
│   │   ├── HomeEvent.kt
│   │   ├── HomeRoute.kt
│   │   ├── HomeScreen.kt
│   │   ├── HomeUiState.kt
│   │   └── HomeViewModel.kt
│   ├── navigation
│   │   ├── Screen.kt
│   │   └── TudeeNavGraph.kt
│   ├── shared
│   │   ├── MainViewModel.kt
│   │   ├── taskdetails
│   │   │   ├── TaskDetailsBottomSheet.kt
│   │   │   ├── TaskDetailsState.kt
│   │   │   └── TaskDetailsViewModel.kt
│   │   └── taskeditor
│   │       ├── TaskEditorActions.kt
│   │       ├── TaskEditorBottomSheetContent.kt
│   │       ├── TaskEditorBottomSheet.kt
│   │       ├── TaskEditorEvent.kt
│   │       ├── TaskEditorUiState.kt
│   │       └── TaskEditorViewModel.kt
│   ├── splash
│   │   ├── onboard
│   │   │   ├── OnboardingRoute.kt
│   │   │   └── OnboardingScreen.kt
│   │   ├── splashscreen
│   │   │   ├── SplashReoute.kt
│   │   │   └── SplashScreen.kt
│   │   └── viewmodel
│   │       └── SplashViewModel.kt
│   ├── tasks
│   │   ├── DatePicker.kt
│   │   ├── MonthHeader.kt
│   │   ├── SwipableTask.kt
│   │   ├── TaskDeleteButton.kt
│   │   ├── TasksRoute.kt
│   │   ├── TasksScreen.kt
│   │   └── viewmodel
│   │       ├── TasksScreenActions.kt
│   │       ├── TasksScreenState.kt
│   │       ├── TasksViewModel.kt
│   │       └── TaskUi.kt
│   ├── tasks_by_category
│   │   ├── TasksByCategoryEvents.kt
│   │   ├── TasksByCategoryRoute.kt
│   │   ├── TasksByCategoryScreenActions.kt
│   │   ├── TasksByCategoryScreen.kt
│   │   ├── TasksByCategoryScreenState.kt
│   │   └── TasksByCategoryViewModel.kt
│   ├── uimodel
│   │   └── TaskUi.kt
│   └── utils
│       ├── errorToMessage.kt
│       ├── EventListener.kt
│       ├── GetCurrentDate.kt
│       ├── getCurrentLocalDateTime.kt
│       ├── Mapper.kt
│       └── millisToLocalDateTime.kt
└── TudeeApp.kt

</pre>


