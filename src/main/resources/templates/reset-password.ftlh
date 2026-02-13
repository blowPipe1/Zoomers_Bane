<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>
<head>
    <title><@spring.message "reset-password.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/password-reset.css'/>">
</head>
<body>

<div class="container">
    <h2><@spring.message "reset-password.header"/></h2>
    <form action="/passwords/reset-password" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="token" value="${token}">
        <div class="form-group">
            <label><@spring.message "reset-password.label"/>:</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>
</div>

</body>
</html>