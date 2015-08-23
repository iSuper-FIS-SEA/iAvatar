<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-user" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div class="list">
                  <table>
                      <thead>
                          <tr>

                              <g:sortableColumn property="name" title="${message(code: 'user.name.label', default: 'Name')}" />

                              <g:sortableColumn property="email" title="${message(code: 'user.email.label', default: 'Email')}" />

                              <g:sortableColumn property="avatarPath" title="${message(code: 'user.avatarPath.label', default: 'Avatar')}" />

                          </tr>
                      </thead>
                      <tbody>
                      <g:each in="${userList}" status="i" var="user">
                          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                              <td><g:link action="show" id="${user}">${fieldValue(bean: user, field: "name")}</g:link></td>

                              <td>${fieldValue(bean: user, field: "email")}</td>

                              <td><img src="/avatar/${user.name}.png" /></td>

                          </tr>
                      </g:each>
                      </tbody>
                  </table>
              </div>

            <div class="pagination">
                <g:paginate total="${userCount ?: 0}" />
            </div>
        </div>
    </body>
</html>
