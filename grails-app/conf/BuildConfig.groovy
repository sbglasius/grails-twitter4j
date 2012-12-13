grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenRepo "http://twitter4j.org/maven2"
	}

	dependencies {
        runtime group:"org.twitter4j", name:"twitter4j-core", version:"3.0.2"
        runtime group:"org.twitter4j", name:"twitter4j-async", version:"3.0.2"
        runtime group:"org.twitter4j", name:"twitter4j-stream", version:"3.0.2"
    }

	plugins {

	}
}
