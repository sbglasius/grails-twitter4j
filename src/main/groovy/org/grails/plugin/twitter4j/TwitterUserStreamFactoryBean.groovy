package org.grails.plugin.twitter4j

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.FactoryBean

import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.ConfigurationBuilder

@Slf4j
class TwitterUserStreamFactoryBean implements FactoryBean<TwitterStream> {

    def configuration

    TwitterStream getObject() {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        configuration.each { key, value ->
            cb."$key" = value
        }

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance()

        log.debug "finish building tweet listener..."

        twitterStream
    }

    Class<TwitterStream> getObjectType() { TwitterStream }

    boolean isSingleton() { true }
}