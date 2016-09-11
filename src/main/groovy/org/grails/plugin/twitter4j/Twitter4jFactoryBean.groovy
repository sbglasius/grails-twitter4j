package org.grails.plugin.twitter4j

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.FactoryBean

import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

@Slf4j
class Twitter4jFactoryBean implements FactoryBean<Twitter> {

    def configuration

    Twitter getObject() {

        ConfigurationBuilder cb = new ConfigurationBuilder()
        configuration.each { key, value ->
            cb."$key" = value
        }

        Twitter twitter = new TwitterFactory(cb.build()).getInstance()

        log.debug "finish building tweet connection..."

        return twitter
    }

    Class<Twitter> getObjectType() { Twitter }

    boolean isSingleton() { true }
}
