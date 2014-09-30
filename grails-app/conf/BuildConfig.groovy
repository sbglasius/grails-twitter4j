grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo 'http://twitter4j.org/maven2'
    }

    dependencies {
        def twitter4jVersion = '4.0.2'
        compile "org.twitter4j:twitter4j-core:$twitter4jVersion"
        compile "org.twitter4j:twitter4j-stream:$twitter4jVersion"
        compile "org.twitter4j:twitter4j-async:$twitter4jVersion"
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
