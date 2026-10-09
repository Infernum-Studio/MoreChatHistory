plugins {
	`java-library`
	`maven-publish`
	id("net.neoforged.moddev")
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

base {
	archivesName = providers.gradleProperty("archives_base_name")
}

java {
	withSourcesJar()
	toolchain.languageVersion = JavaLanguageVersion.of(21)
}

neoForge {
	version = providers.gradleProperty("neo_version").get()

	runs {
		register("client") {
			client()
		}
	}

	mods {
		register("morechathistory") {
			sourceSet(sourceSets.main.get())
		}
	}
}

tasks.processResources {
	val props = mapOf(
		"version" to version,
		"minecraft_version_range" to providers.gradleProperty("minecraft_version_range").get(),
		"neo_version_range" to providers.gradleProperty("neo_version_range").get(),
	)
	inputs.properties(props)

	filesMatching("META-INF/neoforge.mods.toml") {
		expand(props)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
	options.release = 21
}

tasks.jar {
	inputs.property("archivesName", base.archivesName)

	from("LICENSE") {
		rename { "${it}_${base.archivesName.get()}" }
	}
}

// configure the maven publication
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			artifactId = base.archivesName.get()
			from(components["java"])
		}
	}
}
