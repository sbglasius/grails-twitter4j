grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true
    legacyResolve true

    repositories {
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://twitter4j.org/maven2"
	}

	dependencies {
        compile group:"org.twitter4j", name:"twitter4j-core", version:"3.0.3"
        compile group:"org.twitter4j", name:"twitter4j-async", version:"3.0.3"
        compile group:"org.twitter4j", name:"twitter4j-stream", version:"3.0.3"
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
    }
	plugins {
	    // plugins for the build system only
            build ":tomcat:7.0.42"
            build ":release:3.0.0"

            // plugins for the compile step
            compile ":scaffolding:2.0.0"
            compile ':cache:1.1.1'
            build ":rest-client-builder:1.0.2"

            // plugins needed at runtime but not for compilation
            runtime ":hibernate:3.6.10.1" // or ":hibernate4:4.1.11.1"
            runtime ":database-migration:1.3.5"
            runtime ":jquery:1.10.2"
            runtime ":resources:1.2"
            // Uncomment these (or add new ones) to enable additional resources capabilities
            //runtime ":zipped-resources:1.0.1"
            //runtime ":cached-resources:1.1"
            //runtime ":yui-minify-resources:0.1.5"
	}
}

