<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "forgot-password.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/password-reset.css'/>">
</head>
<body>

<div class="container">
    <h2><@spring.message "forgot-password.body.header"/>?</h2>
    <p><@spring.message "forgot-password.instruction"/></p>

    <#if error??>
        <div class="error">${error}</div>
    </#if>

    <#if message??>
        <div class="success">${message}</div>
    </#if>

    <form action="/passwords/forgot-password" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="email"><@spring.message "common.email"/>:</label><br>
            <input type="email" id="email" name="email" required
                   placeholder="<@spring.message "common.email.placeholder"/>" value="${email!''}">
        </div>

        <button type="submit" class="btn-submit"><@spring.message "common.send-btn"/></button>
    </form>

    <p><a href="/login"><@spring.message "forgot-password.link"/></a></p>
</div>

</body>
</html>