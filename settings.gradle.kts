pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()
        maven("https://jitpack.io")
        }
    }

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        dependencyResolutionManagement {
            repositories {
                google()
                mavenCentral()
                maven("https://jitpack.io")
            }
        }

    }
    }



    rootProject.name = "WeStocked"
    include(":app")
