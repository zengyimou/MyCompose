import java.util.Properties

pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			google()
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
			val credentialFile = file(rootDir.absolutePath + "/maven.properties")
			val properties = Properties()
			properties.load(credentialFile.inputStream())

			assert(properties["mavenUsername"] != null)
			assert(properties["mavenPassword"] != null)
			assert(properties["mavenReleaseRepo"] != null)

			maven {
				credentials {
					username = properties["mavenUsername"] as String
					password = properties["mavenPassword"] as String
				}
				url = uri(properties["mavenReleaseRepo"] as String)
			}
		}
}

rootProject.name = "MyCompose"
include(":app")
 