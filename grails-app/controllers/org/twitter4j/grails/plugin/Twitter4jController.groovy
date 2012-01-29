package org.twitter4j.grails.plugin

import twitter4j.Twitter
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import twitter4j.auth.RequestToken
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.TwitterException
import javax.servlet.http.HttpServletResponse

class Twitter4jController {

    Twitter4jService twitter4jService
    def beforeInterceptor = {
        if(ConfigurationHolder.config.twitter.disableTwitter4jController) {
            log.debug("Twitter4jController is disabled")
            response.sendError HttpServletResponse.SC_NOT_FOUND
            return false
        }
        return true
    }
    def index = {
        def consumerKey = ConfigurationHolder.config.twitter4j.OAuthConsumerKey ?: ''
        def consumerSecret = ConfigurationHolder.config.twitter4j.OAuthConsumerSecret ?: ''
        [consumerKey: consumerKey, consumerSecret:consumerSecret]
    }

    def requestToken = {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(params.consumerKey,params.consumerSecret)
        RequestToken requestToken = twitter.getOAuthRequestToken()
        session.twitter = twitter
        session.requestToken = requestToken
        [consumerKey: params.consumerKey, consumerSecret:params.consumerSecret, url: requestToken.authorizationURL]
    }

    def verifyPin = {
        Twitter twitter = session.twitter
        RequestToken requestToken = session.requestToken
        AccessToken accessToken
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, params.pin)
        } catch(TwitterException te) {
            if(401 == te.getStatusCode()){
                flash.message = "Unable to get the access token. ${te.message}"
                redirect(action: 'requestToken', params:[consumerKey: params.consumerKey, consumerSecret:params.consumerSecret])
            }
        }
        session.twitter = null
        session.requestToken = null
        [consumerKey: params.consumerKey, consumerSecret:params.consumerSecret, accessToken: accessToken]
    }

    def status = {
     }

    def update = {
        twitter4jService.connect()
        try {
            twitter4jService.updateStatus(params.statusMessage)
            flash.message = "Posted status: ${params.statusMessage} on ${twitter4jService.screenName}"
        } catch (TwitterException te) {
            flash.error = te.message
        }

        render(view: 'status')


    }
}
