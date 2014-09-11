import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.codehaus.groovy.grails.commons.GrailsApplication
import twitter4j.*
import org.twitter4j.grails.plugin.TwitterUserStreamFactoryBean

class Twitter4jGrailsPlugin {

    // the plugin version
    def version = "4.0.4"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.4 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Twitter4j for Grails"
    def author = "Soeren Berg Glasius, Arthur Neves"
    def authorEmail = "soeren@glasius.dk, arthurnn@gmail.com"
    def description = 'Wraps the Twitter4j API by Groovy delegation (see http://www.twitter4j.org for API documentation and examples)'

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/twitter4j"

    // Extra (optional) plugin metadata

    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Daniel Gerbaudo", email: "info@danielgerbaudo.com" ],
                       [ name: "Ricardo Vilella", email: "vilellaricardo@gmail.com" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
        GrailsApplication grailsApplication

        grailsApplication = new DefaultGrailsApplication()

        def prop = grailsApplication.config.twitter.userListenerClass
        if(prop){
            def clazz = application.classLoader.loadClass(prop)
            log.debug "Register twitter user listenet[${clazz}]"
            twitterUserListener(clazz)
        }else{
            log.debug "There is no Tweeter User Listener to register."
        }

        twitterStream(TwitterUserStreamFactoryBean){
			configuration = grailsApplication.config.twitter.'default'
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
        if(!ctx) {
            log.error("Application context not found. Cannot execute shutdown code.")
            return
        }
        def twitterStream = ctx.getBean("twitterStream")
        if(twitterStream) {
            twitterStream.shutdown()
        }
    }

    def scheduleStream = {ctx ->
        if(ctx.containsBean('twitterUserListener')){
            def twitterUserListener = ctx.getBean('twitterUserListener')
            TwitterStream twitterStream = ctx.getBean('twitterStream')
            twitterStream.addListener(twitterUserListener);
            twitterStream.user();

            log.debug "Twitter User Stream Created.."
        }
    }
}
