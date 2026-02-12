<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "login.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/login.css'/>">
</head>
<body>

<div class="container">
    <div style="text-align: right; margin-bottom: 20px;">
        <span><@spring.message "common.language.select"/>:</span>
        <a href="?lang=ru" style="margin-left: 10px; text-decoration: none;">RU</a>
        <a href="?lang=en" style="margin-left: 10px; text-decoration: none;">EN</a>
    </div>

    <h2><@spring.message "login.body.header"/></h2>
    <#if RequestParameters.error??>
        <span class="error-message"><@spring.message "login.error-message"/></span>
    </#if>
    <form action="/process_login" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="username"><@spring.message "common.email"/></label>
            <input type="email" id="username" name="username" class="form-group input" placeholder="<@spring.message "common.email.placeholder"/>" required>
        </div>

        <div class="form-group">
            <label for="password"><@spring.message "common.password"/></label>
            <input type="password" id="password" name="password" class="form-group input" placeholder="<@spring.message "common.password.placeholder"/>" required>
        </div>

        <button type="submit" class="btn-submit"><@spring.message "login.btn"/></button>
    </form>
    <a href="/register-form" class="register-link"><@spring.message "login.registration-link"/></a>
    <a href="/passwords/forgot-password/form" class="register-link"><@spring.message "login.forgot-password-link"/></a>
</div>
</body>
</html>