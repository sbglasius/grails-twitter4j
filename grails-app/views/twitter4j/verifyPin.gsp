<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Request token</title>
    <meta name="layout" content="main" />
  </head>
  <body>
  <h1>Twitter settings</h1>
  <p>Make the following entry in your Config.groovy</p>
<pre>
  twitter {
    disableTwitter4jController = false  // To avoid intruders to use controller alltogether.
    'default' {
      debugEndabled            = false
      oAuthConsumerKey         = '${consumerKey}'
      oAuthConsumerSecret      = '${consumerSecret}'
      oAuthAccessToken:        = '${accessToken.token}'
      oAuthAccessTokenSecret:  = '${accessToken.tokenSecret}'
    }
  }
</pre>
  And you are ready to use the twitter API without logging in. You can define more than one twitter account by replacing 'default' with
  a name of your choice.
  </body>
</html>