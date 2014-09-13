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
        def twitter4jVersion = '4.0.2'
        compile group: "org.twitter4j", name: "twitter4j-core", version: twitter4jVersion
        compile group: "org.twitter4j", name: "twitter4j-stream", version: twitter4jVersion
        compile group: "org.twitter4j", name: "twitter4j-async", version: twitter4jVersion
    }

    plugins {
        build(":release:3.0.1",
                ":rest-client-builder:2.0.3") {
            export = false
        }
    }
}

