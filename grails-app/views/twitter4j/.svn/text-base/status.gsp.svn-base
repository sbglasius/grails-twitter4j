<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Enter consumer key and secret</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1>Test the twitter options</h1>
    <p>Here you can send messages to your twitter account. Please remember to secure this!</p>
    <g:if test="${flash.error}">
      <p style="color: red">${flash.error}</p>
    </g:if>
  <g:if test="${flash.message}">
    <p style="color: green">${flash.message}</p>
  </g:if>
    <g:form method="get">
      <label for="status">Status: </label><g:textField name="statusMessage" value="${statusMessage}"/><br/>
      <g:actionSubmit action="update" value="Update status"/><br/>
    </g:form>
  </body>
</html>