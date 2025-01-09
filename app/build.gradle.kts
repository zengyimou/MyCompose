plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.mib.mycompose"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.mib.mycompose"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {

		getByName("debug") {
			isDebuggable = true
			isShrinkResources = false
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
		getByName("release") {
			isDebuggable = false
			isShrinkResources = true
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	val composeVersion = "1.7.6"

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.8.1")
	implementation(platform("androidx.compose:compose-bom:2023.03.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
	implementation("androidx.navigation:navigation-compose:2.7.5")
	implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

	implementation("androidx.compose.material:material:$composeVersion")
	implementation("androidx.compose.material:material-icons-extended:$composeVersion")
	implementation("androidx.compose.foundation:foundation:$composeVersion")
	implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
	implementation("androidx.compose.animation:animation:$composeVersion")
	implementation("androidx.compose.runtime:runtime:$composeVersion")
	implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
	implementation("androidx.compose.ui:ui-tooling:$composeVersion")

	// 下拉刷新
	implementation("com.google.accompanist:accompanist-swiperefresh:0.21.2-beta")
	//Paging 3.0
	implementation("androidx.paging:paging-compose:3.2.1")
	implementation("androidx.paging:paging-runtime-ktx:3.2.1")

	implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

	api("com.squareup.okhttp3:okhttp:4.10.0")
	api("com.squareup.retrofit2:retrofit:2.9.0")
	api("com.squareup.retrofit2:converter-gson:2.9.0")

	api("io.github.jeremyliao:live-event-bus-x:1.8.0")

	debugImplementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
	releaseImplementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
}