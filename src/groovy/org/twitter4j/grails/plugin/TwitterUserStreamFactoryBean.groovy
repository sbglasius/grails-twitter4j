package org.twitter4j.grails.plugin

import org.apache.log4j.Logger
import org.springframework.beans.factory.FactoryBean

import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.ConfigurationBuilder

class TwitterUserStreamFactoryBean implements FactoryBean<TwitterStream> {

	private Logger log = Logger.getLogger(getClass().name)

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
