package org.twitter4j.grails.plugin

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

import javax.servlet.http.HttpServletResponse

class Twitter4jController {

    def twitter4jService

    def beforeInterceptor = {
        if (grailsApplication.config.twitter4j.enableTwitter4jController) {
            return true
        }
        log.debug("Twitter4jController is disabled")
        response.sendError HttpServletResponse.SC_NOT_FOUND
        return false
    }

    def index() {
        def conf = grailsApplication.config.twitter4j
        [consumerKey: conf.OAuthConsumerKey ?: '', consumerSecret: conf.OAuthConsumerSecret ?: '']
    }

    def requestToken(String consumerKey, String consumerSecret) {
        Twitter twitter = new TwitterFactory().instance
        twitter.setOAuthConsumer(consumerKey, consumerSecret)
        RequestToken requestToken = twitter.getOAuthRequestToken()
        session.twitter = twitter
        session.requestToken = requestToken
        [consumerKey: consumerKey, consumerSecret: consumerSecret, url: requestToken.authorizationURL]
    }

    def verifyPin(String consumerKey, String consumerSecret, String pin) {
        Twitter twitter = session.twitter
        RequestToken requestToken = session.requestToken
        AccessToken accessToken
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin)
        } catch (TwitterException te) {
            if (401 == te.getStatusCode()) {
                flash.message = "Unable to get the access token. ${te.message}"
                redirect(action: 'requestToken', params: [consumerKey: consumerKey, consumerSecret: consumerSecret])
            }
        }
        session.removeAttribute('twitter')
        session.removeAttribute('requestToken')
        [consumerKey: consumerKey, consumerSecret: consumerSecret, accessToken: accessToken]
    }

    def status() {}

    def update(String statusMessage) {
        try {
            twitter4jService.updateStatus(statusMessage)
            flash.message = "Posted status: ${statusMessage} on ${twitter4jService.screenName}"
        } catch (TwitterException te) {
            flash.error = te.message
        }
        render(view: 'status')
    }
}
