grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
    }
    log "warn"
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo "http://twitter4j.org/maven2"
    }

    dependencies {
        compile group: "org.twitter4j", name: "twitter4j-core", version: "4.0.2"
        compile group: "org.twitter4j", name: "twitter4j-stream", version: "4.0.2"
        compile group: "org.twitter4j", name: "twitter4j-async", version: "4.0.2"
    }

    plugins {
        build(":release:3.0.1",
                ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}

