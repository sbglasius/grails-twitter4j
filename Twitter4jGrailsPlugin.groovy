class Twitter4jGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "1.3 > *"

    def author = "Soeren Berg Glasius"
    def authorEmail = "soeren@glasius.dk"
    def title = "Twitter4j for Grails"
    def description = 'Wraps the Twitter4j API by Groovy delegation (see http://www.twitter4j.org for API documentation and examples)'

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/twitter4j"

    def dependsOn = [:]
    def pluginExcludes = [
            "grails-app/domain/**",
            "grails-app/taglib/**",
            "grails-app/utilites/**",
            "grails-app/views/error.gsp",
            "grails-app/layouts/**",
            "**/test/**"

    ]

    def doWithWebDescriptor = { xml ->}

    def doWithSpring = {}

    def doWithDynamicMethods = { ctx ->}

    def doWithApplicationContext = { applicationContext ->}

    def onChange = { event ->}

    def onConfigChange = { event ->}
}
