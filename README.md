# 🌤 Tudee – Jetpack Compose Task MAnagement App

As a Team, our task is to develop a personal task management app for Android.

---

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
│   ├── database
│   │   ├── CategoryDao.kt
│   │   ├── TaskDao.kt
│   │   └── TudeeDatabase.kt
│   ├── mapper
│   │   ├── CategoryMapper.kt
│   │   └── TaskMapper.kt
│   ├── model
│   │   ├── CategoryEntity.kt
│   │   └── TaskEntity.kt
│   ├── service
│   │   ├── CategoryServiceImp.kt
│   │   ├── MainServiceImpl.kt
│   │   ├── SplashService.kt
│   │   └── TasksServiceImp.kt
│   └── util
│       ├── Constants.kt
│       └── safeCall.kt
├── design_system
│   ├── color
│   │   ├── darkThemeColor.kt
│   │   ├── lightThemeColor.kt
│   │   └── TudeeColors.kt
│   ├── component
│   │   ├── AlertBottomSheet.kt
│   │   ├── AppBar.kt
│   │   ├── button_type
│   │   │   ├── FabButton.kt
│   │   │   ├── NegativeButton.kt
│   │   │   ├── NegativeTextButton.kt
│   │   │   ├── PrimaryButton.kt
│   │   │   ├── SecondaryButton.kt
│   │   │   └── TextButton.kt
│   │   ├── CategoryBottomSheet.kt
│   │   ├── CategoryItem.kt
│   │   ├── DatePickerDialog.kt
│   │   ├── DayCard.kt
│   │   ├── DefaultTextField.kt
│   │   ├── HeaderContent.kt
│   │   ├── LabelIconBox.kt
│   │   ├── NavBar.kt
│   │   ├── NoTasksSection.kt
│   │   ├── ParagraphTextField.kt
│   │   ├── Priority.kt
│   │   ├── Slider.kt
│   │   ├── TabsBar.kt
│   │   ├── TaskCard.kt
│   │   ├── ThemeSwitch.kt
│   │   ├── TudeeSnackBar.kt
│   │   └── TudeeTopBar.kt
│   ├── resources
│   │   └── TudeeResources.kt
│   ├── text_style
│   │   ├── defaultTextStyle.kt
│   │   ├── Font.kt
│   │   └── TudeeTextStyle.kt
│   └── theme
│       ├── Theme.kt
│       └── TudeeTheme.kt
├── di
│   ├── appModule.kt
│   └── dataModule.kt
├── domain
│   ├── model
│   │   ├── Category.kt
│   │   └── task
│   │       ├── Task.kt
│   │       ├── TaskPriority.kt
│   │       └── TaskStatus.kt
│   ├── service
│   │   ├── CategoriesService.kt
│   │   ├── MainService.kt
│   │   ├── SplashService.kt
│   │   └── TasksService.kt
│   └── util
│       ├── DomainError.kt
│       └── Result.kt
├── MainActivity.kt
├── presentation
│   ├── categories
│   │   ├── CategoriesRoute.kt
│   │   ├── CategoriesScreenActions.kt
│   │   ├── CategoriesScreenEvents.kt
│   │   ├── CategoriesScreen.kt
│   │   ├── CategoriesScreenState.kt
│   │   └── CategoryViewModel.kt
│   ├── home
│   │   ├── composable
│   │   │   ├── CardOverView.kt
│   │   │   ├── NoTask.kt
│   │   │   ├── OverViewSection.kt
│   │   │   ├── SliderStatus.kt
│   │   │   ├── TaskSection.kt
│   │   │   ├── TitleOverView.kt
│   │   │   └── TopSlider.kt
│   │   ├── HomeActions.kt
│   │   ├── HomeEvent.kt
│   │   ├── HomeRoute.kt
│   │   ├── HomeScreen.kt
│   │   ├── HomeUiState.kt
│   │   └── HomeViewModel.kt
│   ├── navigation
│   │   ├── Screen.kt
│   │   └── TudeeNavGraph.kt
│   ├── shared
│   │   ├── MainViewModel.kt
│   │   ├── taskdetails
│   │   │   ├── TaskDetailsBottomSheet.kt
│   │   │   ├── TaskDetailsState.kt
│   │   │   └── TaskDetailsViewModel.kt
│   │   └── taskeditor
│   │       ├── TaskEditorActions.kt
│   │       ├── TaskEditorBottomSheetContent.kt
│   │       ├── TaskEditorBottomSheet.kt
│   │       ├── TaskEditorEvent.kt
│   │       ├── TaskEditorUiState.kt
│   │       └── TaskEditorViewModel.kt
│   ├── splash
│   │   ├── onboard
│   │   │   ├── OnboardingRoute.kt
│   │   │   └── OnboardingScreen.kt
│   │   ├── splashscreen
│   │   │   ├── SplashReoute.kt
│   │   │   └── SplashScreen.kt
│   │   └── viewmodel
│   │       └── SplashViewModel.kt
│   ├── tasks
│   │   ├── DatePicker.kt
│   │   ├── MonthHeader.kt
│   │   ├── SwipableTask.kt
│   │   ├── TaskDeleteButton.kt
│   │   ├── TasksRoute.kt
│   │   ├── TasksScreen.kt
│   │   └── viewmodel
│   │       ├── TasksScreenActions.kt
│   │       ├── TasksScreenState.kt
│   │       ├── TasksViewModel.kt
│   │       └── TaskUi.kt
│   ├── tasks_by_category
│   │   ├── TasksByCategoryEvents.kt
│   │   ├── TasksByCategoryRoute.kt
│   │   ├── TasksByCategoryScreenActions.kt
│   │   ├── TasksByCategoryScreen.kt
│   │   ├── TasksByCategoryScreenState.kt
│   │   └── TasksByCategoryViewModel.kt
│   ├── uimodel
│   │   └── TaskUi.kt
│   └── utils
│       ├── errorToMessage.kt
│       ├── EventListener.kt
│       ├── GetCurrentDate.kt
│       ├── getCurrentLocalDateTime.kt
│       ├── Mapper.kt
│       └── millisToLocalDateTime.kt
└── TudeeApp.kt

</pre>


