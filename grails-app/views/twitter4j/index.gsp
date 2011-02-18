<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Enter consumer key and secret</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1>Enter your Consumer information</h1>
    <g:form method="get">
      <label for="consumerKey">consumerKey</label><g:textField name="consumerKey" value="${consumerKey}"/><br/>
      <label for="consumerKey">consumerSecret</label><g:textField name="consumerSecret" value="${consumerSecret}"/><br/>
      <g:actionSubmit action="requestToken" value="Request token"/><br/>
    </g:form>
  </body>
</html>