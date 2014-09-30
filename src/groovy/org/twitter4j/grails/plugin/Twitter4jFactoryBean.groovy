package org.twitter4j.grails.plugin

import org.apache.log4j.Logger
import org.springframework.beans.factory.FactoryBean

import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class Twitter4jFactoryBean implements FactoryBean<Twitter> {

	private Logger log = Logger.getLogger(getClass().name)

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
