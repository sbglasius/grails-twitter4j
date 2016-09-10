package org.grails.plugin.twitter4j

import grails.plugins.*
import groovy.util.logging.Slf4j
import twitter4j.TwitterStream

@Slf4j
class GrailsTwitter4jGrailsPlugin extends Plugin {

    def grailsVersion = "3.0.0 > *"

    def title = "Twitter4j for Grails"

    def description = 'Wraps the Twitter4j API by Groovy delegation (see http://www.twitter4j.org for API documentation and examples)'
    def documentation = "http://sbglasius.github.io/grails-twitter4j/"
    def license = "APACHE"
    def author = "Soeren Berg Glasius"
    def authorEmail = "soeren@glasius.dk"
    def developers = [
            [name: 'Arthur Neves', email: 'arthurnn@gmail.com'],
            [name: "Daniel Gerbaudo", email: "info@danielgerbaudo.com"],
            [name: "Rubén Salinas", email: "rubensalinasgarcia@gmail.com"],
            [name: "Ricardo Vilella", email: "vilellaricardo@gmail.com"],
            [name: 'Soeren Berg Glasius', email: 'soeren@glasius.dk'],
            [name: 'Burt Beckwith', email: 'burt@burtbeckwith.com'],
            [name: 'Roberto Perez Alcolea', email: 'roberto@perezalcolea.info'],
    ]

    def profiles = ['web']

    def issueManagement = [system: "GitHub", url: "https://github.com/sbglasius/grails-twitter4j"]

    def scm = [url: "https://github.com/sbglasius/grails-twitter4j"]

    Closure doWithSpring() {
        { ->
            def twitterConfig = grailsApplication.config.twitter4j

            def d = configureBeans.clone()
            d.delegate = delegate
            d(twitterConfig)
        }
    }

    void doWithApplicationContext() {
        scheduleStream(applicationContext)
    }

    void onConfigChange(Map<String, Object> event) {
        // The event is the same as for 'onChange'.
        Map twitterConfig = event.application.config.twitter4j
        final c = configureBeans.clone()
        def beans = beans {
            c.delegate = delegate
            c(twitterConfig)
        }

        beans.registerBeans(event.ctx)
    }

    void onShutdown(Map<String, Object> event) {
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
            def clazz = grailsApplication.classLoader.loadClass(userListenerClass)
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
