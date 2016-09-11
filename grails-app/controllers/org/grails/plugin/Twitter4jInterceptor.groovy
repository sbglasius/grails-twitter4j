package org.grails.plugin

import org.springframework.beans.factory.annotation.Value

import javax.servlet.http.HttpServletResponse

/**
 * Determines if Twitter4j controller is enabled.
 * If not, returns a 404
 */
class Twitter4jInterceptor {

    @Value('${twitter4j.enableTwitter4jController:false}')
    Boolean enableTwitter4jController

    boolean before() {
        if (enableTwitter4jController) {
            return true
        }

        log.debug("Twitter4jController is disabled")
        response.sendError HttpServletResponse.SC_NOT_FOUND
        return false
    }

}
