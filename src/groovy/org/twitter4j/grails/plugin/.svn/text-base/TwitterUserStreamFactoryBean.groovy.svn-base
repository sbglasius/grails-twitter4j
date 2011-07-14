package org.twitter4j.grails.plugin

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

class TwitterUserStreamFactoryBean implements FactoryBean{
	Logger log = Logger.getLogger(getClass())
	
	def configuration
	
	Object getObject() {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		configuration.each { key, value ->
			cb."$key" = value
		}
		
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		
		log.debug "finish building tweet listener..."
		
		return twitterStream
	}

	Class getObjectType() { TwitterStream }

	boolean isSingleton() { true }
	
	
	
}
