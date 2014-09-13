package org.twitter4j.grails.plugin

import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class Twitter4jService {

    def grailsApplication
    static transactional = false

    @Delegate
    Twitter twitter = connect()

    def connect(account = 'default') {
        def twitterConfiguration = getTwitterConfiguration(account)

        ConfigurationBuilder cb = new ConfigurationBuilder();
        twitterConfiguration.each { key, value ->
            cb."$key" = value
        }
        def twitterFactory = new TwitterFactory(cb.build())
        return twitterFactory.getInstance()
    }

    private getTwitterConfiguration(account) {
        def configuration = grailsApplication.config.twitter."$account"
        if(!configuration) {
            throw new IllegalArgumentException("Missing 'twitter.$account' configuration in your Config.groovy file")
        }
        return configuration
    }
}
