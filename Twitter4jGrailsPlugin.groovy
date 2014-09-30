import org.twitter4j.grails.plugin.Twitter4jFactoryBean
import org.twitter4j.grails.plugin.TwitterUserStreamFactoryBean

import twitter4j.TwitterStream

class Twitter4jGrailsPlugin {

    // the plugin version (Follows Twitter4j library version + number for plugin revision)
    def version = "4.0.4.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    def title = "Twitter4j for Grails"
    def description = 'Wraps the Twitter4j API by Groovy delegation (see http://www.twitter4j.org for API documentation and examples)'
    def documentation = "http://sbglasius.github.io/grails-twitter4j/"
    def license = "APACHE"
    def developers = [
        [name: 'Arthur Neves',        email: 'arthurnn@gmail.com'],
        [name: "Daniel Gerbaudo",     email: "info@danielgerbaudo.com"],
        [name: "Rubén Salinas",       email: "rubensalinasgarcia@gmail.com"],
        [name: "Ricardo Vilella",     email: "vilellaricardo@gmail.com"],
        [name: 'Soeren Berg Glasius', email: 'soeren@glasius.dk']
    ]
    def issueManagement = [system: "GitHub", url: "https://github.com/sbglasius/grails-twitter4j"]
    def scm = [url: "https://github.com/sbglasius/grails-twitter4j/settings"]

    def doWithSpring = {
        Map twitterConfig = application.config.twitter4j

        def d = configureBeans.clone()
        d.delegate = delegate
        d(twitterConfig)
    }

    def doWithApplicationContext = { applicationContext ->
        scheduleStream(applicationContext)
    }

    def onConfigChange = { event ->
        // The event is the same as for 'onChange'.
        Map twitterConfig = event.application.config.twitter4j
        final c = configureBeans.clone()
        def beans = beans {
            c.delegate = delegate
            c(grailsClass)
        }

        beans.registerBeans(event.ctx)
    }

    def onShutdown = { event ->
        def ctx = event.ctx
        if (!ctx) {
            log.error("ApplicationContext not found. Cannot execute shutdown code.")
            return
        }

        ctx.twitterStream?.shutdown()
    }

    private scheduleStream = { ctx ->
        if (!ctx.containsBean('twitterUserListener')) {
            return
        }

        TwitterStream twitterStream = ctx.twitterStream
        twitterStream.addListener ctx.twitterUserListener
        twitterStream.user()

        log.debug "Twitter User Stream Created.."
    }

    private configureBeans = { twitterConfig ->
        def userListenerClass = twitterConfig.userListenerClass
        if (userListenerClass) {
            def clazz = application.classLoader.loadClass(userListenerClass)
            log.debug "Register twitter user listenet[${clazz}]"
            twitterUserListener(clazz)
        } else {
            log.debug "There is no Tweeter User Listener to register."
        }

        def defaultConfig = twitterConfig.'default'
        if (!defaultConfig) {
            log.error("Remember to configure twitter4j")
            return
        }

        twitterStream(TwitterUserStreamFactoryBean) {
            configuration = defaultConfig
        }

        twitter4jService(Twitter4jFactoryBean) {
            configuration = defaultConfig
        }
    }
}
