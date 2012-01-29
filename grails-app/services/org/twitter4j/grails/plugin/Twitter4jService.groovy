package org.twitter4j.grails.plugin

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.AccessToken

class Twitter4jService {

    static transactional = false

    @Delegate @Lazy
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
        def configuration = ConfigurationHolder.config.twitter."$account"
        if(!configuration) {
            throw new IllegalArgumentException("Missing 'twitter.$account' configuration in your Config.groovy file")
        }
        return configuration
    }
}
