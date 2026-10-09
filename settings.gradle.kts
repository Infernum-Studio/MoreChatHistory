pluginManagement {
	repositories {
		gradlePluginPortal()
		maven("https://maven.neoforged.net/releases")
	}

	plugins {
		id("net.neoforged.moddev") version providers.gradleProperty("moddev_version")
	}
}
