<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Request token</title>
    <meta name="layout" content="main" />
   </head>
  <body>
  <h1>Enter your Consumer information</h1>
  <g:if test="${flash.message}">
    <p style="color: red; font-weight: bold">${flash.message}</p>
  </g:if>
  <p>Open the <a href="${url}" target="_blank">Twitter Auth page to get your PIN</a> </p>
  <p>Enter the pin here, and click Verify Pin</p>
  <g:form method="get">
      <label for="pin">PIN:</label><g:textField name="pin" value=""/><br/>
      <g:hiddenField name="consumerKey" value="${consumerKey}"/><br/>
      <g:hiddenField name="consumerSecret" value="${consumerSecret}"/><br/>
      <g:actionSubmit action="verifyPin" value="Verify pin"/>
    </g:form>
 </body>
</html>