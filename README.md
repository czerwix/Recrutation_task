Recrutation_task
==================

This is the repository for a recruitment application, the entire project was completed in about 1
working day. Some shortcuts are taken to lower the amount of time spent on configuring the
application rather than showing the architecture.

# Development Environment

The Project uses the Gradle build system. As mentioned above I have not implemented certain features
to lower development time, below is a mention of what could have been implemented

Omitted functionality that could have been implemented:

1. Build variants for debug/release and configuration setup.
2. Libraries setup - all libraries are now setup in-app level `build.gradle`, this could have been
   extracted to separate modules like `build-logic` folder, it would define project-specific
   convention plugins, used to keep a single source of truth for common module configurations. This
   the approach is would be based on
   [https://developer.squareup.com/blog/herding-elephants/](https://developer.squareup.com/blog/herding-elephants/)
   and
   [https://github.com/jjohannes/idiomatic-gradle](https://github.com/jjohannes/idiomatic-gradle).
   By setting up convention plugins in `build-logic`, we can avoid duplicated build script setup,
   messy `subproject` configurations, without the pitfalls of the `buildSrc` directory.

# Architecture

The app uses a combination of MVVM/MVI approaches. We can define 3 distinct layers of the
application `data`/`domain`/`ui`, a simple writeup on the topic can be found here
[https://developer.android.com/topic/architecture](https://developer.android.com/topic/architecture)
.

To limit the amount of time spent on configuration no code has been extracted to modules(
folders outside of app scope), this is something that I have in almost every project, this allows to
define implementation hierarchy between modules so we know code id is reusable, has proper SOLID
principles and ensure architecture layers are separated and implemented as needed.

Quick summary of layers
`Data` - handles all data transfer, DTOs, DB models and all logic regarding extracting and mapping
logic to domain layer models.
`domain` - Contains all UseCases that handle thread work and handles all work needed by ViewModels,
can combine data from multiple sources and handle long-running tasks.
`UI` - contains screens and view models and all android depended on resources.

# Ui

The screens do not look exactly like Zeplin definitions. this is all to save time, I have used
the default component from material design. this can be customised to look exactly like zeplin but for
the sake of time, I have chosen to use android ready components.

functionality omitted:
- Animation for titleBar going into sticky TopBar on the albumsScreen.
- labels for the `Genre` and `Visit album` button on AlbumDetailsScreen.


The project uses the newest material3 compose elements and ui/navigation stack.

# Modularization
As mentioned before modularisation has been omitted. Example extracted project would look like this:
-app
-navigation
-di
-MainActivity/VamaApp
-common_data
-common_ui
-design_system
-common_database
-feature_album
-ui components
-data/domain in necessary. this can be further extracted to separate modules like (`feature_album-data`, `feature_album-domain`)

#Techstack
- `Coil` - for AsyncImage loading.
- `Room` - for Database implementation.
- `Koin` - for DI implementation
- `Retrofit/Okhttp` - for Networking implementation
- all other android specific libraries for compose/multidex/lifecycle etc.

# Testing
noo testing was included in the scope of the project
