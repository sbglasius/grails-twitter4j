grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    inherits("global") {}
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenRepo "http://twitter4j.org/maven2"
	}

	dependencies {
        //runtime group:"org.twitter4j", name:"twitter4j-core", version:"2.1.11"
        runtime group:"org.twitter4j", name:"twitter4j-core", version:"2.2.6"
        runtime group:"org.twitter4j", name:"twitter4j-async", version:"2.2.6"
        runtime group:"org.twitter4j", name:"twitter4j-stream", version:"2.2.6"
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
    }
}
