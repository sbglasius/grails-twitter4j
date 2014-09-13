import grails.util.Holder
import grails.util.Holders
import org.twitter4j.grails.plugin.TwitterUserStreamFactoryBean
import twitter4j.TwitterStream

class Twitter4jGrailsPlugin {

    // the plugin version (Follows Twitter4j library version + number for plugin revision)
    def version = "4.0.4.0-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.4 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Twitter4j for Grails"
    def author = "Soeren Berg Glasius"
    def authorEmail = "soeren@glasius.dk"
    def description = 'Wraps the Twitter4j API by Groovy delegation (see http://www.twitter4j.org for API documentation and examples)'

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/twitter4j"

    // Extra (optional) plugin metadata

    def license = "APACHE"

    // Any additional developers beyond the author specified above.
    def developers = [
            [name: 'Arthur Neves', email: 'arthurnn@gmail.com'],
            [name: "Daniel Gerbaudo", email: "info@danielgerbaudo.com"],
            [name: "Ricardo Vilella", email: "vilellaricardo@gmail.com"]
    ]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {

        def prop = application.config.twitter.userListenerClass
        if (prop) {
            def clazz = application.classLoader.loadClass(prop)
            log.debug "Register twitter user listenet[${clazz}]"
            twitterUserListener(clazz)
        } else {
            log.debug "There is no Tweeter User Listener to register."
        }

        twitterStream(TwitterUserStreamFactoryBean) {
            configuration = application.config.twitter.'default'
        }
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
        scheduleStream(applicationContext)
    }

    def onChange = { event ->
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        def ctx = event.ctx
        if (!ctx) {
            log.error("Application context not found. Cannot execute shutdown code.")
            return
        }
        def twitterStream = ctx.getBean("twitterStream")
        if (twitterStream) {
            twitterStream.shutdown()
        }
    }

    private scheduleStream = { ctx ->
        if (ctx.containsBean('twitterUserListener')) {
            def twitterUserListener = ctx.getBean('twitterUserListener')
            TwitterStream twitterStream = ctx.getBean('twitterStream')
            twitterStream.addListener(twitterUserListener);
            twitterStream.user();

            log.debug "Twitter User Stream Created.."
        }
    }

}
